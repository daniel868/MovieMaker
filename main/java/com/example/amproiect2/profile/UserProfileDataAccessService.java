package com.example.amproiect2.profile;

import com.example.amproiect2.datastore.FakeMediaDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {

    private final FakeMediaDataStore fakeMediaDataStore;

    @Autowired
    public UserProfileDataAccessService(FakeMediaDataStore fakeImageDataStore) {
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
