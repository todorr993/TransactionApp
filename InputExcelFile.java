package com.company;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class InputExcelFile extends  InputFile
{
    private XSSFWorkbook workbook;

    public InputExcelFile (String path)throws  IOException, InvalidFormatException{
        File file = new File(path);
        workbook = new XSSFWorkbook(file);

    }

    public  HashMap<String, LinkedList<Transaction>> loadData() {

            HashMap<String, LinkedList<Transaction>> users=new HashMap<>();
            XSSFSheet sheet;
            XSSFRow row;


            Iterator sheetIterator = workbook.iterator();

            while (sheetIterator.hasNext()) {
                sheet = (XSSFSheet) sheetIterator.next();
                Iterator rowIterator = sheet.iterator();

                LinkedList<Transaction> transactions = new LinkedList<>();
                double saldo = 0;


                while (rowIterator.hasNext()) {
                    row = (XSSFRow) rowIterator.next();
                    if (row.getCell(0).getStringCellValue().equals("BANK"))
                        saldo = row.getCell(1).getNumericCellValue();
                    else saldo += row.getCell(1).getNumericCellValue();
                    transactions.add(new Transaction(row.getCell(0).getStringCellValue(), row.getCell(1).getNumericCellValue(), saldo));
                }// end while row Iterator

                users.put(sheet.getSheetName(), transactions);
            }//end while sheet iterator

        return users;
    }//end method
}
