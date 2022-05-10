package com.example.amproiect2.video.render;

import com.example.amproiect2.video.animation.ProvideBaseAnimation;
import com.example.amproiect2.video.effects.ThreadTestEffect;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Semaphore;

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

        //emitProgressStatus(0);

        System.out.println(provideBaseAnimation.getAnimatedList().size());
        for (int i = 0; i < provideBaseAnimation.getAnimatedList().size(); i++) {

            BufferedImage resizedBuffered = provideBaseAnimation.getAnimatedList().get(i);

            int width = resizedBuffered.getWidth();
            int height = resizedBuffered.getWidth();

            System.out.println("Width: " + width + " " + "Height: " + height);


            encoder.encodeImage(resizedBuffered);
            int renderPercent = i * 100 / provideBaseAnimation.getAnimatedList().size();
            renderLogger.info("Status: [" + renderPercent + "]%");
        //    emitProgressStatus(renderPercent);
        }

//        System.out.println(provideBaseAnimation.getAnimatedList().size());
//        Semaphore syncSemaphore = new Semaphore(-1);
//
//        List<BufferedImage> imageList1 = provideBaseAnimation.getAnimatedList()
//                .subList(0,
//                (provideBaseAnimation.getAnimatedList().size() / 2));
//        List<BufferedImage> imageList2 = provideBaseAnimation.getAnimatedList()
//                .subList((provideBaseAnimation.getAnimatedList().size() / 2),
//                        provideBaseAnimation.getAnimatedList().size());
//
//        ThreadTestEffect threadTestEffect1 =new ThreadTestEffect(encoder,syncSemaphore,imageList1);
//        ThreadTestEffect threadTestEffect2 =new ThreadTestEffect(encoder,syncSemaphore,imageList2);
//
//        threadTestEffect1.start();
//        threadTestEffect2.start();
//
//        syncSemaphore.acquire();

        //  emitProgressStatus(100);

        closeEncoder();
    }

    private void emitProgressStatus(int progressValue) throws Exception {
        sseEmitterLocal.send(SseEmitter.event()
                .name(guidLocal)
                .data(progressValue));
    }


}
