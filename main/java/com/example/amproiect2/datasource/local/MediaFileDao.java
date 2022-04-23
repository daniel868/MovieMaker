package com.example.amproiect2.datasource.local;

import com.example.amproiect2.entities.MediaFile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaFileDao {
    private final MediaFileRepository mediaFileRepository;

    public MediaFileDao(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    public List<MediaFile> fetchImagesAwsKeys() {
        return fetchAfterExtension(Arrays.asList("png", "jpg", "png", "jpeg"));
    }

    public List<MediaFile> fetchAudioAwsKeys() {
        return fetchAfterExtension(Arrays.asList("wav", "mp3"));
    }

    public void insertMediaFile(String fileName, String folderName) {
        String projectName = "proj1";
        String objectKey = String.format("%s/%s", folderName, fileName);
        mediaFileRepository.save(new MediaFile(
                objectKey, fileName, projectName
        ));
    }

    private List<MediaFile> fetchAfterExtension(List<String> targetFileExtension) {
        return mediaFileRepository.findAll().stream()
                .filter(mediaFile -> {
                    int dotLastIndex = mediaFile.getObjectFileName().lastIndexOf(".");
                    String fileExtension = mediaFile.getObjectFileName().substring(dotLastIndex + 1);
                    return targetFileExtension.contains(fileExtension);
                }).collect(Collectors.toList());
    }



}
