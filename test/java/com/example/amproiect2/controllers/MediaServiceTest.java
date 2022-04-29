package com.example.amproiect2.controllers;

import com.example.amproiect2.entities.LocalFileDto;
import com.example.amproiect2.servicies.MediaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MediaServiceTest {

    @Autowired
    private MediaService mediaService;


    @Test
    void fetchImagesEndpoint() {
        //given
        List<LocalFileDto> underTest = mediaService.fetchImagesEndpoint();
        //then
        assertThat(underTest.size()).isEqualTo(3);
    }

    @Test
    void couldFetchAudioEndpoint() {
        //given
        List<LocalFileDto> underTest = mediaService.fetchAudioEndpoint().stream()
                .map(audioEndpoint -> new LocalFileDto(
                        audioEndpoint.getFileUrl(),
                        "fileName"))
                .collect(Collectors.toList());


        //then
        assertThat(underTest.size()).isEqualTo(1);
    }

}