package com.example.amproiect2.media;

import com.example.amproiect2.datastore.FakeMediaDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MediaRepository {

    private final FakeMediaDataStore fakeMediaDataStore;

    @Autowired
    public MediaRepository(FakeMediaDataStore fakeImageDataStore) {
        this.fakeMediaDataStore = fakeImageDataStore;
    }

    public List<byte[]> getFakeImageList() {
        return fakeMediaDataStore.getImages();
    }

    public List<byte[]> getFakeAudioList() {
        return fakeMediaDataStore.getAudio();
    }

    public FakeMediaDataStore getFakeMediaDataStore() {
        return fakeMediaDataStore;
    }
}
