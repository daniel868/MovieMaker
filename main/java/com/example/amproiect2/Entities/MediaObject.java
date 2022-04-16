package com.example.amproiect2.Entities;

public class MediaObject {
    private final String filePath;
    private final byte[] inputStream;

    public MediaObject(String filePath, byte[] inputStream) {
        this.filePath = filePath;
        this.inputStream = inputStream;
    }

    public String getFilePath() {
        return filePath;
    }

    public byte[] getInputStream() {
        return inputStream;
    }
}
