package com.example.amproiect2.video.render;

import com.example.amproiect2.video.animation.ProvideBaseAnimation;

public class Render extends RenderBase {
    private final static String DEFAULT_NAME = "default.mp4";
    private final ProvideBaseAnimation provideBaseAnimation;
    private String outputFilename;

    public Render(ProvideBaseAnimation provideBaseAnimation) {
        this.provideBaseAnimation = provideBaseAnimation;
    }

    public Render setOutputFileName(String fileName) {
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

        for (int i = 0; i < provideBaseAnimation.getAnimatedList().size(); i++) {
            encoder.encodeImage(provideBaseAnimation.getAnimatedList().get(i));
            int renderPercent = i * 100 / provideBaseAnimation.getAnimatedList().size();
            renderLogger.info("Status: [" + renderPercent + "]%");
        }

        closeEncoder();
    }
}
