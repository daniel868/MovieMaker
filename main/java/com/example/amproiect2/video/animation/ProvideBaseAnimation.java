package com.example.amproiect2.video.animation;

import com.defano.jsegue.AnimatedSegue;

import java.awt.*;
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
        BufferedImage resized = resizeImage(bufferedImage);
        animatedList.add(resized);
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

    private BufferedImage resizeImage(BufferedImage inputImage) {
        int currentWidth = inputImage.getWidth();
        int currentHeight = inputImage.getHeight();

        if (currentHeight % 2 != 0) {
            currentHeight = currentHeight - 1;
        }
        if (currentWidth % 2 != 0) {
            currentWidth = currentWidth - 1;
        }


        BufferedImage resizedImage = new BufferedImage(currentWidth, currentHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < currentHeight; y++) {
            for (int x = 0; x < currentWidth; x++) {
                resizedImage.setRGB(x, y, new Color(inputImage.getRGB(x, y)).getRGB());
            }
        }

        return resizedImage;
    }
}
