package com.example.reptile.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void writeData(StringBuffer stringBuffer,String filePath){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(filePath),true);
            fileWriter.write(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeExcel(String [] titles,String fileName){

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for (int i=0;i<titles.length;i++){
            sheet.createRow(i);
//            sheet.
        }
    }
}
