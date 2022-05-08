package com.example.amproiect2.controllers;

import com.example.amproiect2.entities.LocalFileDto;
import com.example.amproiect2.servicies.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
public class MediaController {

    private final MediaService mediaService;


    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping(
            path = "/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadImageFile(@RequestParam("file") MultipartFile file) {
        mediaService.uploadFile(file, "images");
    }

    @PostMapping(
            path = "/audio/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadAudioFile(MultipartFile file) {
        mediaService.uploadFile(file, "audio");
    }

    @GetMapping("/images/download")
    public List<LocalFileDto> retrieveImageFilePath() {
        return mediaService.fetchImagesEndpoint();
    }

    @GetMapping("/audio/download")
    public List<LocalFileDto> retrieveAudioFilesPath() {
        return mediaService.fetchAudioEndpoint();
    }

    @GetMapping("/images/download/{imageIndex}")
    public byte[] getImageFile(@PathVariable("imageIndex") int imageIndex) {
        return mediaService.getImageFileByIndex(imageIndex);
    }

    @GetMapping("/audio/download/{audioIndex}")
    public byte[] getAudioFile(@PathVariable("audioIndex") int audioIndex) {
        return mediaService.getAudioFileByIndex(audioIndex);
    }



}
