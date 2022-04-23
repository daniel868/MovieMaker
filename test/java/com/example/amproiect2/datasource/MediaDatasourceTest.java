package com.example.amproiect2.datasource;

import com.example.amproiect2.entities.LocalFileDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MediaDatasourceTest {

    @Autowired
    private MediaDatasource mediaDatasource;

    @Test
    void couldDownloadImageFiles() {
        //when
        List<byte[]> underTest = mediaDatasource.fetchImageFiles();

        //then
        assertThat(underTest.size()).isEqualTo(3);
    }

    @Test
    void couldMapDataForDatafile() {
        //given
        List<LocalFileDto> parseImageEndpoint = mediaDatasource.createParseImageEndpoint();

        //then
        assertThat(parseImageEndpoint.size()).isEqualTo(6);

    }
}