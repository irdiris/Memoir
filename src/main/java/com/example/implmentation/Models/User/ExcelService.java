package com.example.implmentation.Models.User;

import com.example.implmentation.Models.Researcher.Researcher;
import com.example.implmentation.Models.Researcher.ResearcherRepository;
import com.example.implmentation.Models.Student.Student;
import com.example.implmentation.Models.Student.StudentRepository;
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
private final StudentRepository studentRepository;
private final ResearcherRepository researcherRepository;
@Autowired
    public ExcelService(UserRepository userRepository, StudentRepository studentRepository, ResearcherRepository researcherRepository) {
        this.userRepository = userRepository;
    this.studentRepository = studentRepository;
    this.researcherRepository = researcherRepository;
}


    public void registerUser(User user) throws IOException {
       if (user.getType().contains("Student")){
           System.out.println("student");
        int sheetIndex = 0;
        String columnName = "id";


        FileInputStream file = new FileInputStream(new File("src/main/resources/Student.xlsx"));
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
            user.setBirthDate((matchingRow.getCell(4).getStringCellValue()));
            user.setAddress(matchingRow.getCell(5).getStringCellValue());
            user.setPhone((int) matchingRow.getCell(6).getNumericCellValue());
            user.setState("Active");
           studentRepository.save(Student.builder()
                           .type("Student")
                           .user(user)
                           .institution(matchingRow.getCell(7).getStringCellValue())
                           .level(matchingRow.getCell(8).getStringCellValue())
                           .specialty(matchingRow.getCell(9).getStringCellValue())
                           .build());

        } else {
            System.out.println("No matching row found");
        }

        // Close the workbook and the input stream
        workbook.close();
        file.close();
       }
       else {
           System.out.println("Researcher");
           int sheetIndex = 0;
           String columnName = "id";


           FileInputStream file = new FileInputStream(new File("src/main/resources/Researcher.xlsx"));
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
               user.setBirthDate((matchingRow.getCell(4).getStringCellValue()));
               user.setAddress(matchingRow.getCell(5).getStringCellValue());
               user.setPhone((int) matchingRow.getCell(6).getNumericCellValue());
               user.setState("Active");
               researcherRepository.save(Researcher.builder()
                               .type("Researcher")
                       .user(user)
                       .facility(matchingRow.getCell(7).getStringCellValue())
                       .position(matchingRow.getCell(8).getStringCellValue())
                       .rank(matchingRow.getCell(5).getStringCellValue())
                       .build());

           } else {
               System.out.println("No matching row found");
           }

           // Close the workbook and the input stream
           workbook.close();
           file.close();}

    }
}
