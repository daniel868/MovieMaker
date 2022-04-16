package com.example.amproiect2.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }


    @PostMapping(
            path = "/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(file);
    }

    @PostMapping(
            path = "/audio/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadAudioFile(MultipartFile file) {
        userProfileService.uploadAudioFile(file);
    }

    @GetMapping("/images/download")
    public List<String> downloadImages() {
        return userProfileService.downloadAllImages();
    }

    @GetMapping("/images/download/{imageIndex}")
    public byte[] getImageByIndex(@PathVariable("imageIndex") int imageIndex) {
        return Optional.of(userProfileService.downloadImages()
                        .get(imageIndex))
                .orElse(new byte[0]);
    }

    @GetMapping("/audio/download")
    public List<String> downloadAudio() {
        return userProfileService.downloadAudioFiles();
    }

    @GetMapping("/audio/download/{audioIndex}")
    public byte[] getAudioByIndex(@PathVariable("audioIndex") int audioIndex) {
        return userProfileService.downloadAudioFileByIndex(audioIndex);

    }

}
