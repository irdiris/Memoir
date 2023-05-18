package com.example.implmentation.Models.User;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
@Service
public class ExcelService {

private final UserRepository userRepository;
@Autowired
    public ExcelService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void registerUser(User user) throws IOException {

        int sheetIndex = 0;
        String columnName = "id";


        FileInputStream file = new FileInputStream(new File("src/main/resources/Book 2.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);


        // Get the sheet and find the column index
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int columnIndex = -1;
        Row firstRow = sheet.getRow(0);
        Iterator<Cell> cellIterator = firstRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getStringCellValue().equals(columnName)) {
                columnIndex = cell.getColumnIndex();
                break;
            }
        }

        // Find the row that matches the value in the specified column
        Row matchingRow = null;
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if ((row.getCell(columnIndex).getNumericCellValue()) == user.getId()) {
                matchingRow = row;
                break;
            }
        }

        // Do something with the matching row
        if (matchingRow != null) {
            user.setFirstName(matchingRow.getCell(1).getStringCellValue());
            user.setLastName(matchingRow.getCell(2).getStringCellValue());
            user.setGender(matchingRow.getCell(3).getStringCellValue());
            user.setBirthDate(matchingRow.getCell(4).getStringCellValue());
            user.setAddress(matchingRow.getCell(5).getStringCellValue());
            user.setPhone((int) matchingRow.getCell(6).getNumericCellValue());
            userRepository.save(user);

        } else {
            System.out.println("No matching row found");
        }

        // Close the workbook and the input stream
        workbook.close();
        file.close();

    }
}