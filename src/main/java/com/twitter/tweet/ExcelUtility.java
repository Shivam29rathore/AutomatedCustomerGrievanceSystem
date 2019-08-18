package com.twitter.tweet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.twitter.tweet.model.Tweet;

public class ExcelUtility {
    public static void writeToExcelInMultiSheets(final String fileName, final String sheetName,
                                                 final Tweet tweet) {
        OutputStream fos = null;
        XSSFWorkbook workbook = null;
        FileInputStream fis = null;
        int columnCount;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet(sheetName);
            List<String> fieldNames = getFieldNamesForClass(tweet.getClass());
            if (null == sheet) {
                sheet = workbook.createSheet(sheetName);
                columnCount = 0;
                Row row = sheet.createRow(sheet.getLastRowNum());
                for (String fieldName : fieldNames) {
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(fieldName);
                }
            }

            Class<? extends Object> classz = tweet.getClass();

            Row row = sheet.createRow(sheet.getLastRowNum()+1);
            columnCount = 0;
            for (String fieldName : fieldNames) {
                Cell cell = row.createCell(columnCount);
                Method method = null;
                try {
                    method = classz.getMethod("get" + capitalize(fieldName));
                } catch (NoSuchMethodException nme) {
                    method = classz.getMethod("get" + fieldName);
                }
                Object value = method.invoke(tweet, (Object[]) null);
                if (value != null) {
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    } else if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Double) {
                        cell.setCellValue((Double) value);
                    }
                }
                columnCount++;
            }
            if (fis != null)
                fis.close();

            fos = new FileOutputStream(file);
            
            workbook.write(fos);
            fos.flush();
            if (fos != null)
                fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getFieldNamesForClass(Class<?> clazz) throws Exception {
        List<String> fieldNames = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fieldNames.add(fields[i].getName());
        }
        return fieldNames;
    }

    private static String capitalize(String s) {
        if (s.length() == 0)
            return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
