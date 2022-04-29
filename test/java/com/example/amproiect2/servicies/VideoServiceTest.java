package com.example.amproiect2.servicies;

import com.example.amproiect2.datasource.MediaDatasource;
import com.example.amproiect2.entities.VideoRenderDto;
import com.example.amproiect2.video.effects.EffectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoServiceTest {

    private VideoService underTest;

    @Autowired
    private MediaDatasource mediaDatasource;

    private VideoRenderDto videoRenderDto;


    @BeforeEach
    void setUp() {
        videoRenderDto = new VideoRenderDto(EffectFactory.ScrollRightEffect, 3, 4);
        underTest = new VideoService(mediaDatasource);
    }

    @Test
    void couldRenderEffect() throws Exception {
//        CompletableFuture<byte[]> completableFuture = underTest.renderPreview(videoRenderDto);
//
//        FileInputStream expectedFile = new FileInputStream(EffectFactory.ScrollRightEffect + ".mp4");
//
//        assertThat(expectedFile.readAllBytes().length).isEqualTo(completableFuture.get().length);
    }
}