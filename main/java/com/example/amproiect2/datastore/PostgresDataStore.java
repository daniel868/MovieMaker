package com.example.amproiect2.datastore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class PostgresDataStore {
    private static final List<String> filesKey;

    static {
        filesKey = List.of(
                "user1/10.png",
                "user1/12.png",
                "user1/cat.jpg",
                "user1/ClaseAbtracte_Interfete.jpg",
                "user1/java_datastructures.jpg",
                "user1/WhatsApp Image 2021-10-16 at 16.09.48.jpeg",
                "user1/Ubi.png"
        );
    }

    private List<String> imageFileKeys;

    @Autowired
    public PostgresDataStore() {
        fetchImageFileKeys();
    }

    private void fetchImageFileKeys() {
        imageFileKeys = new ArrayList<>();

        imageFileKeys.addAll(filesKey);
    }

    public List<String> getImageFileKeys() {
        return imageFileKeys;
    }
}
