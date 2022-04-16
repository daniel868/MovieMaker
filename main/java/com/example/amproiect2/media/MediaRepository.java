package com.example.amproiect2.media;

import com.example.amproiect2.datastore.MediaDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class MediaRepository {

    //TODO: add media data store entity
    //TODO: add local database entity
    private final MediaDataStore mediaDataStore;

    @Autowired
    public MediaRepository(MediaDataStore mediaDataStore) {
        this.mediaDataStore = mediaDataStore;
    }

    public List<byte[]> getFakeImageList() {
        return mediaDataStore.getImageBlobFiles();
    }

    public List<byte[]> getFakeAudioList() {
        return new LinkedList<>();
    }


    public MediaDataStore getMediaDataStore() {
        return mediaDataStore;
    }
}
