package com.qa.gorest.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

    private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/APITestData.xlsx";
    private static Workbook book;
    private static Sheet sheet;

    public static Object[][] getTestData(String sheetName) throws InvalidFormatException {
        Object data[][] = null;
        FileInputStream ip = null;

        try {
            ip = new FileInputStream(TEST_DATA_SHEET_PATH);
            book = WorkbookFactory.create(ip);
            sheet = book.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet with name '" + sheetName + "' does not exist in the workbook");
            }

            int numRows = sheet.getLastRowNum();
            int numCols = sheet.getRow(0).getLastCellNum();
            data = new Object[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if (sheet.getRow(i + 1) != null && sheet.getRow(i + 1).getCell(j) != null) {
                        data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
                    } else {
                        data[i][j] = ""; // Assign default value or handle as needed
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel file not found at path: " + TEST_DATA_SHEET_PATH, e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading Excel file", e);
        } finally {
            try {
                if (book != null) {
                    book.close();
                }
                if (ip != null) {
                    ip.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }
}
