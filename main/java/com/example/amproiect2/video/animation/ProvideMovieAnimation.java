package com.example.amproiect2.video.animation;

import java.util.List;
import java.util.concurrent.Semaphore;

public class ProvideMovieAnimation extends ProvideBaseAnimation {
    private final List<String> animatedImagesPath;

    public ProvideMovieAnimation(Semaphore semaphore, List<String> animatedImagesPath) {
        super(semaphore);

        this.animatedImagesPath = animatedImagesPath;
    }

    @Override
    public void run() {

    }
}
