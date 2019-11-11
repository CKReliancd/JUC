package com.atguigu;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.sun.xml.internal.ws.client.RequestContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public final class Test {

    public static StringBuffer doSomething(StringBuffer buff){

        //1.创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //2.在建立的工作簿中添加一个sheet，对应Excell文件中的工作簿，并设置工作簿名称
        HSSFSheet sheet = wb.createSheet();
        Object[][] datas = {{"区域", "总销售额(万元)", "总利润(万元)简单的表格"}, {"江苏省", 9045, 2256}, {"广东省", 3000, 690}};

        HSSFRow row;
        HSSFCell cell;

        for (int i = 0; i < datas.length; i++) {
            row = sheet.createRow(i);//创建表格行
            for (int j = 0; j < datas[i].length; j++) {
                cell = row.createCell(j);//根据表格行创建单元格
                cell.setCellValue(String.valueOf(datas[i][j]));
            }
        }
        ByteOutputStream output = new ByteOutputStream();
        ByteArrayInputStream swapStream = null;
        FileInputStream input = null;
        try {
            input = new FileInputStream("Members.xls");
            wb.write(output);
            swapStream = new ByteArrayInputStream(output.toByteArray());

            FileService service = FileServiceFactory.getAttachmentFileService();
            RequestContext ctx = RequestContext.getOrCreate();

            String path = FileNameUtils.getExportFileName(ctx.getTenantId(), ctx.getAccountId(), dataEntityType.getAppId(),
                    dataEntityType.getName() + UUID.randomUUID().toString(), "Members.xls");

            String url = service.upload(new FileItem("Members.xls", path, swapStream));

            String fileserver = System.getProperty("fileserver");

            view.openUrl(fileserver + url);

            swapStream.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (swapStream != null) {
                try {
                    swapStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                output.close();

            }

            swapStream = null;
            output = null;
            System.out.println("导出成功");
        }

    }

    }


}
