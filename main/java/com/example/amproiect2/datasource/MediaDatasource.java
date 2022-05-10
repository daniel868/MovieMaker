package com.example.amproiect2.datasource;

import com.example.amproiect2.config.BucketName;
import com.example.amproiect2.datasource.local.MediaFileDao;
import com.example.amproiect2.datasource.storage.AmazonFileStore;
import com.example.amproiect2.datasource.storage.StorageFileDto;
import com.example.amproiect2.entities.LocalFileDto;
import com.example.amproiect2.entities.MediaFile;
import com.example.amproiect2.servicies.MediaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaDatasource {
    private final MediaFileDao mediaFileDao;
    private final AmazonFileStore amazonFileStore;
    private List<byte[]> cachedImages;
    private List<byte[]> cachedAudio;
    private List<MediaFile> imagesKeys;
    private List<MediaFile> audioKeys;

    public MediaDatasource(MediaFileDao mediaFileDao, AmazonFileStore amazonFileStore) {
        this.mediaFileDao = mediaFileDao;
        this.amazonFileStore = amazonFileStore;

        cachedImages = fetchImageFiles();
        cachedAudio = fetchAudioFiles();
    }

    public List<byte[]> fetchImageFiles() {
        imagesKeys = mediaFileDao.fetchImagesAwsKeys();

        return Optional.of(amazonFileStore.downloadAllFiles(
                        BucketName.PROFILE_IMAGE.getBucketName(),
                        imagesKeys
                ))
                .orElseThrow(() -> new RuntimeException("Could not get image files"));

    }

    public List<byte[]> fetchAudioFiles() {
        audioKeys = mediaFileDao.fetchAudioAwsKeys();

        return Optional.of(amazonFileStore.downloadAllFiles(
                        BucketName.PROFILE_IMAGE.getBucketName(),
                        audioKeys
                ))
                .orElseThrow(() -> new RuntimeException("Could not get audio files"));
    }

    public List<LocalFileDto> createParseImageEndpoint() {
        List<LocalFileDto> localImageFiles = new ArrayList<>();

        for (int i = 0; i < cachedImages.size(); i++) {
            String path = String.format("%s%d", MediaService.URL_IMAGE, i);
            String fileName = imagesKeys.get(i).getObjectFileName();
            String id = String.valueOf(imagesKeys.get(i).getId());
            localImageFiles.add(new LocalFileDto(id, path, fileName));
        }
        return localImageFiles;
    }

    public List<LocalFileDto> createParseAudioEndpoint() {

        List<LocalFileDto> localAudioFiles = new ArrayList<>();

        for (int i = 0; i < cachedAudio.size(); i++) {
            String path = String.format("%s%d", MediaService.URL_AUDIO, i);
            String fileName = audioKeys.get(i).getObjectFileName();
            String id = String.valueOf(audioKeys.get(i).getId());
            localAudioFiles.add(new LocalFileDto(id, path, fileName));
        }
        return localAudioFiles;
    }

    public void saveFile(StorageFileDto storageFileDto, String folderName) {
        try {
            mediaFileDao.insertMediaFile(storageFileDto.getFileName(), folderName);
            amazonFileStore.save(
                    storageFileDto.getFilePath(),
                    storageFileDto.getFileName(),
                    Optional.of(storageFileDto.getMetaData()),
                    storageFileDto.getFileInputStream()
            );

            cachedImages = fetchImageFiles();
            cachedAudio = fetchAudioFiles();
        } catch (Exception e) {
            throw new RuntimeException("Could not save file: " + e.getMessage());
        }
    }

    public void deleteFile(Long id) {
        MediaFile toDeleteFile = mediaFileDao.getFileById(id);
        amazonFileStore.deleteFile(BucketName.PROFILE_IMAGE.getBucketName(),
                toDeleteFile.getAwsObjectKey());
        mediaFileDao.deleteById(id);
    }

    public List<byte[]> getCachedImages() {
        return cachedImages;
    }

    public List<byte[]> getCachedAudio() {
        return cachedAudio;
    }
}
