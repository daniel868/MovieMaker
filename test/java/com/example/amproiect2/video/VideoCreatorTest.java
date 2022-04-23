package com.example.amproiect2.video;

import com.example.amproiect2.video.tests.VideoCreator;
import com.example.amproiect2.video.tests.providers.VideoAnimator;
import com.example.amproiect2.video.tests.providers.VideoEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

class VideoCreatorTest {

    private VideoCreator underTest;

    @BeforeEach
    void setUp() {
        Semaphore empty = new Semaphore(2);
        Semaphore full = new Semaphore(-1);

        VideoEncoder videoEncoder = new VideoEncoder(full);
        VideoAnimator videoAnimator = new VideoAnimator(full, empty);

        underTest = new VideoCreator(videoEncoder, videoAnimator);
    }

    @Test
    void couldRenderVideo() {
        underTest.startRenderingVideo();
    }
}