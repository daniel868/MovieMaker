package com.example.amproiect2.controllers;


import com.example.amproiect2.config.BucketName;
import com.example.amproiect2.datasource.storage.AmazonFileStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class FileStoreTest {

    @Autowired
    private AmazonFileStore fileStore;

    @Test
    void assertThatFileStoreIsWorking() throws Exception {
        String imageKey = "user1/ClaseAbtracte_Interfete.jpg";
        byte[] retrievedImage = fileStore.download(BucketName.PROFILE_IMAGE.getBucketName(), imageKey);

        assertThat(retrievedImage.length).isGreaterThan(0);
    }
}
