package com.example.amproiect2.datasource.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class VideoLoadService {

    @Async
    public CompletableFuture<byte[]> loadVideoFile() throws Exception {

        System.out.println(Thread.currentThread().getName());

        File file = new File("C:\\Users\\danit\\OneDrive\\Documents\\Bandicam\\bd_proiect.mp4");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        byteArrayOutputStream.write(fileInputStream.readAllBytes());

        return CompletableFuture.completedFuture(byteArrayOutputStream.toByteArray());
    }
}
