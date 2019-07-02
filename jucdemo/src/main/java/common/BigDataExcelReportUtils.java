package common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class BigDataExcelReportUtils {

    private OutputStream outputStream;
    private SXSSFWorkbook writeWorkbook;
    private int rowCount;
    private Sheet sheet;

    private CellStyle style;
    private CellStyle style2;

    public BigDataExcelReportUtils(String fullName) throws FileNotFoundException {
        File file = new File(fullName);
        this.outputStream = new FileOutputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeWorkbook = new SXSSFWorkbook(workbook, 100);
        rowCount = 0;

        Font font = writeWorkbook.createFont();
        font.setColor(HSSFColor.DARK_BLUE.index2);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体

        style = writeWorkbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setFont(font);

        style2 = writeWorkbook.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
    }

    public void append(String sheetName, String[][] data) {
        if (sheet == null) {
            sheet = writeWorkbook.createSheet();
            writeWorkbook.setSheetName(0, sheetName);

            Row row = sheet.createRow(rowCount++);
            String[] header = data[0];
            for (int i = 0; i < header.length; i++) {
                sheet.setColumnWidth(i, 660 * header[i].length());
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置单元格格式
                cell.setCellStyle(style);
            }
        }

        for (int i = 1; i < data.length; i++) {
            Row row = sheet.createRow(rowCount++);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data[i][j]);
                cell.setCellStyle(style2);
            }
        }
    }

    /**
     * 将数据追加到新的sheet
     *
     * @param sheetNum
     * @param sheetName
     * @param data
     */
    public void appendToNewSheet(int sheetNum, String sheetName, String[][] data) {
        rowCount = 0;
        sheet = writeWorkbook.createSheet();
        writeWorkbook.setSheetName(sheetNum, sheetName);

        Row headRow = sheet.createRow(rowCount++);
        String[] header = data[0];
        for (int i = 0; i < header.length; i++) {
            sheet.setColumnWidth(i, 660 * header[i].length());
            Cell cell = headRow.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置单元格格式
            cell.setCellStyle(style);
        }

        for (int i = 1; i < data.length; i++) {
            Row dataRow = sheet.createRow(rowCount++);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(data[i][j]);
                cell.setCellStyle(style2);
            }
        }
    }

    /**
     * 创建多个sheet后，刷新输出流
     *
     * @throws IOException
     */
    public void flush() throws IOException {
        writeWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 下载Excel文件
     *
     * @param request
     * @param response
     * @param fileFullName
     * @param fileName
     */
    public void downLoadExportExcel(HttpServletRequest request, HttpServletResponse response, String fileFullName, String fileName) {
        FileInputStream in = null;
        OutputStream o = null;
        File fileLoad = null;
        try {
            response.reset();
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/x-excel");
            fileLoad = new File(fileFullName);
            long fileLength = fileLoad.length();
            String length = String.valueOf(fileLength);
            response.setHeader("Content_Length", length);
            in = new FileInputStream(fileLoad);
            int n = 0;
            byte b[] = new byte[1024];
            o = response.getOutputStream();
            while ((n = in.read(b)) != -1) {
                o.write(b, 0, n);
            }
        } catch (Exception ex) {
            System.out.println("导出excel错误,详细----->" + ex.getMessage());
        } finally {
            ManagerTxt.del(fileLoad);   //为保证服务器不存储过多文件  下载后便删除文件
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
