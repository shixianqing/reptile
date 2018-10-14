package com.example.reptile.util;

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
}
