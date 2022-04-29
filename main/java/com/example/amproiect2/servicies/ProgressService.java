package com.example.amproiect2.servicies;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProgressService {
    private final Path root = Paths.get("uploads/test_");


    public void generatePercent(SseEmitter sseEmitter, String guid) {
        try {

            for (int i = 0; i <= 100; i++) {
                sseEmitter.send(SseEmitter.event()
                        .name(guid)
                        .data(i));

                if (i == 100) {
                    sseEmitter.send(SseEmitter.event()
                            .name(guid)
                            .data(100)
                    );
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            sseEmitter.completeWithError(e);
        }
    }

}
