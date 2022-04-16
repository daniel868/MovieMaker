package com.example.amproiect2.profile;

import com.example.amproiect2.buckets.BucketName;
import com.example.amproiect2.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final static String URL_IMAGE = "http://localhost:8080/api/v1/user-profile/images/download/";
    private final static String URL_AUDIO = "http://localhost:8080/api/v1/user-profile/audio/download/";

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    public void uploadUserProfileImage(MultipartFile file) {
        //check if image is not empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + " ]");
        }

        //if file is an image
        if (!Arrays.asList(
                        ContentType.IMAGE_JPEG.getMimeType(),
                        ContentType.IMAGE_PNG.getMimeType(),
                        ContentType.IMAGE_BMP.getMimeType())
                .contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image");
        }

        //get some metadata from file
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), "user1");
        /*
        try {
            fileStore.save(path, file.getResource().getFilename(), Optional.of(metaData), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        try {
            byte[] uploadedImage = Optional.of(file.getBytes())
                    .orElseThrow(() -> new RuntimeException("Could not read the file"));


            userProfileDataAccessService.getFakeMediaDataStore()
                    .getImages()
                    .add(uploadedImage);
            System.out.println("Reading input image file " + userProfileDataAccessService.getFakeImageList().size());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadAudioFile(MultipartFile multipartFile) {
        try {
            userProfileDataAccessService.getFakeAudioList()
                    .add(multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<byte[]> downloadImages() {
        return userProfileDataAccessService.getFakeImageList();
    }

    public List<String> downloadAllImages() {
        //some call to the database to get all images stored for a specific project

        List<String> imagePath = new ArrayList<>();
        for (int i = 0; i < userProfileDataAccessService.getFakeImageList().size(); i++) {
            String filePath = String.format("%s%d", URL_IMAGE, i);
            imagePath.add(filePath);
        }

        return imagePath;
    }

    public List<String> downloadAudioFiles() {
        //some call to the database to get all images path stored for a specific project
        List<String> audioPath = new ArrayList<>();
        for (int i = 0; i < userProfileDataAccessService.getFakeAudioList().size(); i++) {
            String filePath = String.format("%s%d", URL_AUDIO, i);
            audioPath.add(filePath);
        }
        return audioPath;
    }

    public byte[] downloadAudioFileByIndex(int index) {
        return userProfileDataAccessService
                .getFakeAudioList()
                .get(index);
    }
}
