package com.example.amproiect2.entities;

public class LocalFileDto {

    private String id;
    private String fileUrl;
    private String fileName;

    public LocalFileDto(String id, String fileUrl, String fileName) {
        this.id = id;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}