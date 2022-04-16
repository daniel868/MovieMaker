package com.example.amproiect2;

import com.amazonaws.services.s3.AmazonS3;
import com.example.amproiect2.buckets.BucketName;
import com.example.amproiect2.filestore.AmazonFileStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AmProiect2ApplicationTests {


    @Autowired
    private AmazonS3 s3;

    @Autowired
    private AmazonFileStore fileStore;

    @Test
    void contextLoads() {
        assertThat(s3).isNotNull();
    }

    @Test
    void couldDownloadFileFromS3() {
        //given
        String imageKey = "user1/10.png";
        //when
        byte[] imageDownloaded = fileStore.download(BucketName.PROFILE_IMAGE.getBucketName(), imageKey);
        //then
        assertThat(imageDownloaded).isNotNull();
    }

    @Test
    void couldDownloadListFilesFromS3() {
        //given
        List<String> filesKey = List.of(
                "user1/10.png",
                "user1/12.png",
                "user1/cat.jpg",
                "user1/ClaseAbtracte_Interfete.jpg",
                "user1/java_datastructures.jpg",
                "user1/WhatsApp Image 2021-10-16 at 16.09.48.jpeg"
        );

        List<byte[]> expected = new ArrayList<>();

        for (String key : filesKey) {
            byte[] imageDownloaded = fileStore.download(BucketName.PROFILE_IMAGE.getBucketName(), key);
            expected.add(imageDownloaded);
        }

        assertThat(expected.size()).isEqualTo(filesKey.size());
    }

}
