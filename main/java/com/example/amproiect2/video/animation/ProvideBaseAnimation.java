package com.example.amproiect2.video.animation;

import com.defano.jsegue.AnimatedSegue;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public abstract class ProvideBaseAnimation extends Thread implements IAnimation {
    private final List<BufferedImage> animatedList;

    private final Semaphore releaseBuild;

    protected ProvideBaseAnimation(Semaphore releaseBuild) {
        this.animatedList = new ArrayList<>();
        this.releaseBuild = releaseBuild;
    }

    @Override
    public void onFrameRendered(AnimatedSegue animatedSegue, BufferedImage bufferedImage) {
        animatedList.add(bufferedImage);
    }

    @Override
    public void onSegueAnimationCompleted(AnimatedSegue animatedSegue) {
        releaseBuild.release();
    }

    public List<BufferedImage> getAnimatedList() {
        return animatedList;
    }

    public Semaphore getReleaseBuild() {
        return releaseBuild;
    }
}
