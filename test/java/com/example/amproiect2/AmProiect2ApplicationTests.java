package com.example.amproiect2;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AmProiect2ApplicationTests {


    @Autowired
    private AmazonS3 s3;

    @Test
    void contextLoads() {
        assertThat(s3).isNotNull();
    }

}
