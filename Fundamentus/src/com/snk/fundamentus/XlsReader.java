package com.snk.fundamentus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.snk.fundamentus.models.BalancoPatrimonial;


public class XlsReader {

    public static void main(String[] args) throws IOException {
        FileInputStream file = new FileInputStream(new File("H:\\balanco.xlsx"));

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();

        BalancoPatrimonial balanco = new BalancoPatrimonial();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (row.getRowNum() < 1)
                continue;

            Cell cell = row.getCell(0);

            if (null != cell) {

                String stringCellValue = cell.getStringCellValue();

                if (stringCellValue.equals("Ativo Total")) {
                    balanco.setAtivoCirculante(row.getCell(1).getNumericCellValue() * 1000);
                }
            }

            // case Cell.CELL_TYPE_STRING:
            // case Cell.CELL_TYPE_NUMERIC:

        }
        file.close();
    }
}
