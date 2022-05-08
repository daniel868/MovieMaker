package com.example.amproiect2.servicies;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;

@Service
public class StreamingService {
    private static final String FORMAT = "C:\\Users\\danit\\OneDrive\\Desktop\\%s.mp4";

    public StreamingService() {

    }

    public Mono<byte[]> getVideoFromByte(String title) {
        return Mono.fromSupplier(() -> {
            try (FileInputStream fileInputStream = new FileInputStream(String.format(FORMAT, title))) {
                return fileInputStream.readAllBytes();
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Could not read chuck from file");
        });
    }
}
