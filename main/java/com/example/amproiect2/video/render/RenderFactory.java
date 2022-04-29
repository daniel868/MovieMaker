package com.example.amproiect2.video.render;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.video.animation.ProvideBaseAnimation;
import com.example.amproiect2.video.animation.ProvideMovieAnimation;
import com.example.amproiect2.video.animation.ProvidePreviewAnimation;

import java.util.List;
import java.util.concurrent.Semaphore;

public class RenderFactory {
    public final static String RENDER_PREVIEW = "render_preview";
    public final static String RENDER_MOVIE = "render_movie";

    public static Render provideRender(String renderType, Object animation) {
        Semaphore finishedRendering = new Semaphore(0);

        switch (renderType) {
            case RENDER_MOVIE: {
                ProvideBaseAnimation provideBaseAnimation = new ProvideMovieAnimation(
                        finishedRendering,
                        (List<AnimatedSegue>) animation);
                return new Render(provideBaseAnimation);
            }
            case RENDER_PREVIEW: {
                ProvideBaseAnimation provideBaseAnimation = new ProvidePreviewAnimation(
                        (AnimatedSegue) animation,
                        finishedRendering);
                return new Render(provideBaseAnimation);
            }
        }
        throw new RuntimeException("Could not instantiate render");
    }
}
