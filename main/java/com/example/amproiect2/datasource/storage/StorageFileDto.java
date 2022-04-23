package com.example.amproiect2.datasource.storage;

import java.io.InputStream;
import java.util.HashMap;

public class StorageFileDto {
    private final String filePath;
    private final String fileName;
    private final HashMap<String,String>metaData;
    private final InputStream fileInputStream;

    public StorageFileDto(String filePath, String fileName, HashMap<String, String> metaData, InputStream fileInputStream) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.metaData = metaData;
        this.fileInputStream = fileInputStream;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public HashMap<String, String> getMetaData() {
        return metaData;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }
}