package com.example.amproiect2.datastore;

import com.example.amproiect2.buckets.BucketName;
import com.example.amproiect2.filestore.AmazonFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaDataStore {
    private final PostgresDataStore postgresDataStore;
    private final AmazonFileStore amazonFileStore;

    private List<byte[]> imageBlobFiles;
    private List<byte[]> audioBlobFiles;

    @Autowired
    public MediaDataStore(PostgresDataStore postgresDataStore, AmazonFileStore amazonFileStore) {
        this.postgresDataStore = postgresDataStore;
        this.amazonFileStore = amazonFileStore;

        fetchImageBlobFiles();
    }

    public void fetchImageBlobFiles() {

        imageBlobFiles = new LinkedList<>();

        for (String key : postgresDataStore.getImageFileKeys()) {
            byte[] retrievedImage = Optional.of(amazonFileStore.
                            download(BucketName.PROFILE_IMAGE.getBucketName(), key))
                    .orElseThrow(() -> new RuntimeException("Could not obtain image: " + key));
            imageBlobFiles.add(retrievedImage);
        }
    }

    public void fetchAudioBlobFiles() {

    }

    public List<byte[]> getImageBlobFiles() {
        return this.imageBlobFiles;
    }
}

