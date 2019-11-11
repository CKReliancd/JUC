package com.atguigu;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

public class POI {
    public static void main(String[] args) {


        //1.创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //2.在建立的工作簿中添加一个sheet，对应Excell文件中的工作簿，并设置工作簿名称
        HSSFSheet sheet = wb.createSheet("sheet1");
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

        // 输出到本地
        String excelName = "E:/360Downloads/Members.xls";
        FileOutputStream out = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        ByteArrayInputStream swapStream = null;
        try {
            wb.write(baos);

            swapStream = new ByteArrayInputStream(baos.toByteArray());

            out.flush();
            swapStream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out = null;
            System.out.println("导出成功");
        }


    }
}
