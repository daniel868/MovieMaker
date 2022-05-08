package com.example.amproiect2.datasource.async;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class VideoLoadServiceTest {

    @Autowired
    private ManageVideoFiles underTest;

    @Test
    void couldReadFileFromThread() throws Exception {
        //given
        underTest.deleteFilesExtension();
    }

}