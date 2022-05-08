package com.example.amproiect2.servicies;

import com.example.amproiect2.config.BucketName;
import com.example.amproiect2.datasource.MediaDatasource;
import com.example.amproiect2.datasource.storage.StorageFileDto;
import com.example.amproiect2.entities.LocalFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    public final static String URL_IMAGE = "http://localhost:8080/api/v1/user-profile/images/download/";
    public final static String URL_AUDIO = "http://localhost:8080/api/v1/user-profile/audio/download/";
    private final MediaDatasource mediaDatasource;

    @Autowired
    public MediaService(MediaDatasource mediaDatasource) {
        this.mediaDatasource = mediaDatasource;
    }

    public void uploadFile(MultipartFile file, String folderName) {
        //check if image is not empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + " ]");
        }

        //get some metadata from file
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), folderName);

        try {
            StorageFileDto storageFileDto = new StorageFileDto(path, file.getOriginalFilename(),
                    metaData, file.getInputStream());

            mediaDatasource.saveFile(storageFileDto, folderName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LocalFileDto> fetchImagesEndpoint() {
        return mediaDatasource.createParseImageEndpoint();
    }

    public List<LocalFileDto> fetchAudioEndpoint() {
        return mediaDatasource.createParseAudioEndpoint();
    }

    public byte[] getImageFileByIndex(int index) {
        return Optional.of(mediaDatasource
                        .getCachedImages()
                        .get(index))
                .orElseThrow(() -> new RuntimeException("Could not get image at index " + index));
    }

    public byte[] getAudioFileByIndex(int index) {
        return Optional.of(mediaDatasource
                        .getCachedAudio()
                        .get(index))
                .orElseThrow(() -> new RuntimeException("Could not get audio at index " + index));
    }

    public void deleteFileById(Long id) {
        mediaDatasource.deleteFile(id);
    }

}

