package com.example.amproiect2.datasource.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.amproiect2.entities.MediaFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AmazonFileStore {
    private final AmazonS3 s3;

    @Autowired
    public AmazonFileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path, String fileName, Optional<HashMap<String, String>> optionalMetadata,
                     InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });

        try {
            s3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

    public List<byte[]> downloadAllFiles(String bucketName, List<MediaFile> filesKeys) {
        List<byte[]> files = new ArrayList<>();

        for (MediaFile mediaFile : filesKeys) {
            byte[] expectedFile = Optional.of(download(bucketName, mediaFile.getAwsObjectKey()))
                    .orElseThrow(() -> new RuntimeException("Could not obtain file: " + mediaFile.getAwsObjectKey()));
            files.add(expectedFile);
        }
        return files;
    }

    public byte[] download(String bucketName, String objectKey) {
        try {
            return s3.getObject(new GetObjectRequest(bucketName, objectKey))
                    .getObjectContent()
                    .readAllBytes();
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Could not download file to s3", e);
        }
    }

    public void deleteFile(String bucketName, String objectKey) {
        try {
            s3.deleteObject(bucketName, objectKey);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Could not delete file from AWS: " + objectKey);
        }
    }

}
