package com.example.amproiect2.profile;


import com.example.amproiect2.buckets.BucketName;
import com.example.amproiect2.filestore.FileStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class FileStoreTest {

    @Autowired
    private FileStore fileStore;

    @Test
    void assertThatFileStoreIsWorking() throws Exception {
        String imageKey = "user1/ClaseAbtracte_Interfete.jpg";
        byte[] retrievedImage = fileStore.download(BucketName.PROFILE_IMAGE.getBucketName(), imageKey);

        assertThat(retrievedImage.length).isGreaterThan(0);
    }
}
