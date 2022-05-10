package com.example.amproiect2.video.render;

import com.example.amproiect2.video.animation.ProvideBaseAnimation;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.awt.image.BufferedImage;

public class RenderPreview extends RenderBase {
    private final static String DEFAULT_NAME = "default.mp4";
    private final ProvideBaseAnimation provideBaseAnimation;
    private String outputFilename;


    public RenderPreview(ProvideBaseAnimation provideBaseAnimation) {
        this.provideBaseAnimation = provideBaseAnimation;
    }


    public RenderPreview setOutputFileName(String fileName) {
        this.outputFilename = fileName;
        return this;
    }

    public String getOutputFilename() {
        return outputFilename;
    }


    public void renderMediaContent() throws Exception {
        initEncoder(outputFilename == null ? DEFAULT_NAME : outputFilename);

        emitProgressStatus(0);

        provideBaseAnimation.start();

        provideBaseAnimation.join();

        provideBaseAnimation.getReleaseBuild().acquire();

        for (int i = 0; i < provideBaseAnimation.getAnimatedList().size(); i++) {

            BufferedImage resizedBuffered = provideBaseAnimation.getAnimatedList().get(i);

            encoder.encodeImage(resizedBuffered);

            int renderPercent = i * 100 / provideBaseAnimation.getAnimatedList().size();

            renderLogger.info("Status: [" + renderPercent + "]%");

            emitProgressStatus(renderPercent);
        }

        emitProgressStatus(100);

        closeEncoder();
    }

    private void emitProgressStatus(int progressValue) throws Exception {
        sseEmitterLocal.send(SseEmitter.event()
                .name(guidLocal)
                .data(progressValue));
    }


}
