package com.example.amproiect2.datasource.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
public class ManageVideoFiles {

    @Async
    public void deleteFilesExtension(){
        File folder = new File(".");
        Arrays.stream(folder.listFiles())
                .filter(file->file.getName().endsWith(".mp4"))
                .forEach(File::delete);
    }
}
