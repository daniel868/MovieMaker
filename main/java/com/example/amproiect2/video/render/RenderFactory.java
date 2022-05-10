package com.example.amproiect2.video.render;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.video.animation.ProvideBaseAnimation;
import com.example.amproiect2.video.animation.ProvidePreviewAnimation;
import com.example.amproiect2.video.scripts.ScriptsRender;

import java.util.concurrent.Semaphore;

public class RenderFactory {
    public final static String RENDER_PREVIEW = "render_preview";
    public final static String RENDER_MOVIE = "render_movie";

    public static RenderBase provideRender(String renderType, Object animation) {
        Semaphore finishedRendering = new Semaphore(0);

        switch (renderType) {
            case RENDER_MOVIE: {
                return new RenderMovie((ScriptsRender) animation);
            }
            case RENDER_PREVIEW: {
                ProvideBaseAnimation provideBaseAnimation = new ProvidePreviewAnimation(
                        (AnimatedSegue) animation,
                        finishedRendering);
                return new RenderPreview(provideBaseAnimation);
            }
        }
        throw new RuntimeException("Could not instantiate render");
    }
}
