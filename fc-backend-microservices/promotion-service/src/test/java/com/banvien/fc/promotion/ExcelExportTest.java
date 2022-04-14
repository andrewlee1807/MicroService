package com.banvien.fc.promotion;

import com.banvien.fc.promotion.dto.ProductDTO;
import com.banvien.fc.promotion.entity.BrandEntity;
import com.banvien.fc.promotion.entity.CatGroupEntity;
import com.banvien.fc.promotion.entity.ProductOutletSkuEntity;
import com.banvien.fc.promotion.entity.ProductPromotionBlockedEntity;
import com.banvien.fc.promotion.repository.BrandRepository;
import com.banvien.fc.promotion.repository.CategoryRepository;
import com.banvien.fc.promotion.repository.ProductPromotionBlockedRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExportTest {

    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine(XSSFSheet sheet, XSSFWorkbook workbook) {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        createCell(sheet, row, 0, "User ID", style);
        createCell(sheet, row, 1, "E-mail", style);
        createCell(sheet, row, 2, "Full Name", style);
        createCell(sheet, row, 3, "Roles", style);
        createCell(sheet, row, 4, "Enabled", style);

    }

    private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (int i=0; i<=10; i++) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(sheet, row, columnCount++, i, style);
            createCell(sheet, row, columnCount++, "user.getEmail()", style);
            createCell(sheet, row, columnCount++, "user.getFullName()", style);
            createCell(sheet, row, columnCount++, "user.getRoles().toString()", style);
            createCell(sheet, row, columnCount++, "user.isEnabled()", style);

        }
    }

    private boolean deleteFile(File file) throws IOException {
        if (file != null) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();

                for (File f: files) {
                    deleteFile(f);
                }
            }
            return Files.deleteIfExists(file.toPath());
        }
        return false;
    }

    @Test
    public void export() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet;
        sheet = workbook.createSheet("Users");

        writeHeaderLine(sheet, workbook);
        writeDataLines(sheet, workbook);

        this.deleteFile(new File("D:\\JavaBooks.xlsx"));

        try (FileOutputStream outputStream = new FileOutputStream("D:\\JavaBooks.xlsx")) {
            workbook.write(outputStream);
        }

        workbook.close();
//        https://www.codejava.net/frameworks/spring-boot/export-data-to-excel-example

    }

    @Test
    public void test() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");

        Object[][] bookData = {
                {"Head First Java", "Kathy Serria", 79},
                {"Effective Java", "Joshua Bloch", 36},
                {"Clean Code", "Robert martin", 42},
                {"Thinking in Java", "Bruce Eckel", 35},
        };

        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }


        try (FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx")) {
            workbook.write(outputStream);
        }

    }
}
