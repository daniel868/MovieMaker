package com.example.amproiect2;

import com.example.amproiect2.datasource.local.MediaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmProiect2Application implements CommandLineRunner {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    public static void main(String[] args) {
        SpringApplication.run(AmProiect2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
