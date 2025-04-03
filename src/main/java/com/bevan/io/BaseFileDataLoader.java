package com.bevan.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class BaseFileDataLoader implements DataLoader {
    String filePath;

    public BaseFileDataLoader(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public String read() {
        try {
            String result = FileUtils.readFileToString(new File(filePath), "utf-8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(String data) {
        try{
            FileUtils.writeStringToFile(new File(filePath), data, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
