package com.example.amproiect2.video.animation;

import com.defano.jsegue.AnimatedSegue;

import java.util.List;
import java.util.concurrent.Semaphore;

public class ProvideMovieAnimation extends ProvideBaseAnimation {
    private final List<AnimatedSegue> effects;

    public ProvideMovieAnimation(Semaphore releaseBuild, List<AnimatedSegue> effects) {
        super(releaseBuild);
        this.effects = effects;
    }

    @Override
    public void run() {
        for (AnimatedSegue currentAnimation : effects) {
            currentAnimation.addAnimationObserver(this);
            currentAnimation.addCompletionObserver(this);

            currentAnimation.start();

            try {
                sleep(currentAnimation.getDurationMs());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onSegueAnimationCompleted(AnimatedSegue animatedSegue) {
        if (animatedSegue.equals(effects.get(effects.size() - 1))) {
            this.getReleaseBuild().release();
        }
    }
}
