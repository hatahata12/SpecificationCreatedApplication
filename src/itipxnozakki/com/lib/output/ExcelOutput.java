package itipxnozakki.com.lib.output;

import itipxnozakki.com.lib.data.Journal;
import itipxnozakki.com.lib.data.OutputData;
import itipxnozakki.com.lib.data.Ticket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOutput implements DataOutputInterface {

    private String url;
    private List<OutputData> outputData;

    public ExcelOutput(String _url, List<OutputData> outputData) {
        this.url = _url;
        this.outputData = outputData;
    }
    @Override
    public void outputting(String outputName) {

        try {

            XSSFWorkbook wb = new XSSFWorkbook();
            FileOutputStream fileOut;

            for (OutputData output : outputData) {
                for (List<Ticket> ticketList : output.get()) {
                    for (Ticket ticket : ticketList) {
                        String sheetName = ticket.getProjectName() + "|" + ticket.getName();
                        sheetName = sheetName.replaceAll("\\/","・");
                        System.out.println(sheetName);
                        XSSFSheet sheet = wb.createSheet(sheetName);
                        Journal journal = ticket.getData();
                        int rowCount = 0;

                        XSSFRow row = sheet.createRow(rowCount);
                        XSSFCell cell = row.createCell(1);
                        row = sheet.createRow(++rowCount);
                        cell = row.createCell(1);

                        if (ticket.getDescription() != null) {
                        	String[] spritDescription = ticket.getDescription().split("\n");
                        	for (String descript : spritDescription) {
                                row = sheet.createRow(rowCount);
                                cell = row.createCell(1);
                                cell.setCellValue(descript);
                                rowCount++;
                        	}
                        }
                        row = sheet.createRow(rowCount);
                        cell = row.createCell(1);
                        cell.setCellValue(ticket.getStartDate());
                        rowCount=rowCount+2;

                        CellStyle style = null;
                        int tableCount = 0;
                        for (String text : journal.getData()) {
                            if (text.matches("table_.*")) {
                                rowCount++;
                                List<String[]> tables = ticket.getTableMap().get(text);
                                int cellCount = 1;
                            	if (tables != null) {
                            		for (String[] table : tables ) {
                                        XSSFRow tableRow = sheet.createRow(rowCount);
                                        XSSFCell tableCell = tableRow.createCell(cellCount);
                                        style = wb.createCellStyle();
                                        if (tableCount == 0) {
                                            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                                            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                                        }
                                        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
                                        style.setBorderRight(CellStyle.BORDER_MEDIUM);
                                        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
                                        style.setBorderTop(CellStyle.BORDER_MEDIUM);

                                        for (String tableData : table) {
                                            tableCell = tableRow.createCell(cellCount++);
                                            tableCell.setCellValue(tableData);
                                            tableCell.setCellStyle(style);
                                            tableCount++;
                                        }

                                        cellCount = 1;
                                        rowCount++;
                                    }
                                    tableCount = 0;
                                }

                            } else {
                                row = sheet.createRow(rowCount);
                                cell = row.createCell(1);
                                cell.setCellValue(text);
                                rowCount++;
                            }
                        }
                        rowCount++;
                        sheet.autoSizeColumn(1);
                        }


                }
            }

            fileOut = new FileOutputStream(this.url+outputName+".xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

    }

}
