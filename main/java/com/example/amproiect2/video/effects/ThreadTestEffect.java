package com.example.amproiect2.video.effects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.codec.Encoder;
import org.apache.commons.codec.EncoderException;
import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Semaphore;


@AllArgsConstructor
@Getter
public class ThreadTestEffect extends Thread {
    private final AWTSequenceEncoder encoder;
    private final Semaphore syncSemaphore;
    private final List<BufferedImage> bufferedImageList;


    @Override
    public void run() {
        for (BufferedImage bufferedImage : bufferedImageList) {
            try {
                encoder.encodeImage(bufferedImage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        syncSemaphore.release();
    }
}
