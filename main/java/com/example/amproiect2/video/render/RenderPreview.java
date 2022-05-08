package com.example.amproiect2.video.render;

import com.example.amproiect2.video.animation.ProvideBaseAnimation;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

        provideBaseAnimation.start();

        provideBaseAnimation.join();

        emitProgressStatus(0);

        for (int i = 0; i < provideBaseAnimation.getAnimatedList().size(); i++) {
            encoder.encodeImage(provideBaseAnimation.getAnimatedList().get(i));
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
