package com.example.amproiect2.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    public void uploadUserProfileImage(@RequestParam("file") MultipartFile file) {
        mediaService.uploadUserProfileImage(file);
    }

    @PostMapping(
            path = "/audio/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadAudioFile(MultipartFile file) {
        mediaService.uploadAudioFile(file);
    }

    @GetMapping("/images/download")
    public List<String> downloadImages() {
        return mediaService.downloadAllImages();
    }

    @GetMapping("/images/download/{imageIndex}")
    public byte[] getImageByIndex(@PathVariable("imageIndex") int imageIndex) {
        return Optional.of(mediaService.downloadImages()
                        .get(imageIndex))
                .orElse(new byte[0]);
    }

    @GetMapping("/audio/download")
    public List<String> downloadAudio() {
        return mediaService.downloadAudioFiles();
    }

    @GetMapping("/audio/download/{audioIndex}")
    public byte[] getAudioByIndex(@PathVariable("audioIndex") int audioIndex) {
        return mediaService.downloadAudioFileByIndex(audioIndex);

    }

}
