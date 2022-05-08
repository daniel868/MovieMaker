package com.example.amproiect2.datasource.local;

import com.example.amproiect2.datasource.MediaDatasource;
import com.example.amproiect2.entities.MediaFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DeleteAwsTest {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Autowired
    private MediaDatasource mediaDatasource;

    @Autowired
    private MediaFileDao mediaFileDao;

    @Test
    void couldDeleteFileFromAWs() {
        mediaFileDao.deleteById(45L);
    }

    @Test
    void couldGetEntityById() {
        MediaFile expected = mediaFileRepository.findById(40L)
                .orElseThrow(() -> new RuntimeException("Could not get"));

        assertThat(expected).isNotNull();
    }


}
