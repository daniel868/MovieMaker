package com.example.amproiect2.video.animation;

import com.defano.jsegue.AnimatedSegue;

import java.util.concurrent.Semaphore;

public class ProvidePreviewAnimation extends ProvideBaseAnimation {
    private final AnimatedSegue animation;

    public ProvidePreviewAnimation(AnimatedSegue animation, Semaphore finishedBuilding) {
        super(finishedBuilding);
        this.animation = animation;

        animation.addAnimationObserver(this);
        animation.addCompletionObserver(this);
    }

    @Override
    public void run() {
        animation.start();
    }

}
