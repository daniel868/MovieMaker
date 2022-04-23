package com.example.amproiect2.video.tests;

import com.example.amproiect2.video.tests.providers.VideoAnimator;
import com.example.amproiect2.video.tests.providers.VideoEncoder;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class VideoCreator {
    public static List<BufferedImage> animatedImages =
            Collections.synchronizedList(new LinkedList<>());

    private final VideoEncoder videoEncoder;
    private final VideoAnimator videoAnimator;


    public VideoCreator(VideoEncoder videoEncoder, VideoAnimator videoAnimator) {
        this.videoEncoder = videoEncoder;
        this.videoAnimator = videoAnimator;

    }

    public void startRenderingVideo() {

        videoAnimator.start();
        videoEncoder.start();

        try {
            videoEncoder.join();
            videoAnimator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
