package com.example.amproiect2.datasource.local;

import com.example.amproiect2.entities.MediaFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MediaFileDaoTest {

    @Autowired
    MediaFileDao mediaFileDao;

    @Test
    void couldObtainOnlyKeysForImages() {
        //given
        List<MediaFile> underTest = mediaFileDao.fetchImagesAwsKeys();

        //then
        assertThat(underTest.size()).isEqualTo(10);
    }

    @Test
    void couldObtainOnlyKeysForAudioFiles() {
        //given
        List<MediaFile> underTest = mediaFileDao.fetchAudioAwsKeys();

        //then
        assertThat(underTest.size()).isEqualTo(1);
    }

}