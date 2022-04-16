package com.example.amproiect2.media;

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
        mediaService.uploadFile(file, "user1");
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
    public List<String> retrieveImageFilePath() {
        return mediaService.retrieveImagesFilePath();
    }

    @GetMapping("/images/download/{imageIndex}")
    public byte[] getImageFile(@PathVariable("imageIndex") int imageIndex) {
        return mediaService.getImageFileByIndex(imageIndex);
    }

    @GetMapping("/audio/download")
    public List<String> retrieveAudioFilesPath() {
        return mediaService.retrieveAudioFilesPath();
    }

    @GetMapping("/audio/download/{audioIndex}")
    public byte[] getAudioFile(@PathVariable("audioIndex") int audioIndex) {
        return mediaService.downloadAudioFileByIndex(audioIndex);
    }

}
