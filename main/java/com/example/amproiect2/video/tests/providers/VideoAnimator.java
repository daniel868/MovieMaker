package com.example.amproiect2.video.tests.providers;

import com.defano.jsegue.AnimatedSegue;
import com.defano.jsegue.SegueAnimationObserver;
import com.defano.jsegue.SegueCompletionObserver;
import com.example.amproiect2.video.effects.Effect;
import com.example.amproiect2.video.effects.EffectFactory;
import com.example.amproiect2.video.tests.VideoCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Semaphore;


public class VideoAnimator extends Thread {

    public static String IMAGE1_FiLE = "C:\\Users\\danit\\OneDrive\\Pictures\\Video Projects\\10.png";
    public static String IMAGE2_FiLE = "C:\\Users\\danit\\OneDrive\\Pictures\\Video Projects\\12.png";
    private Logger logger = LoggerFactory.getLogger(VideoAnimator.class);
    private final Semaphore full;
    private final Semaphore empty;


    public VideoAnimator(Semaphore full, Semaphore empty) {
        this.full = full;
        this.empty = empty;
    }

    @Override
    public void run() {
        try {
            empty.acquire();
            startAnimation();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAnimation() throws Exception {
        BufferedImage image1, image2;

        //preview effects => source = destination
        //add effects => source = currentImage; destination = currentImage+1

        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                image1 = ImageIO.read(new File(IMAGE1_FiLE));
                image2 = ImageIO.read(new File(IMAGE2_FiLE));
            } else {
                image1 = ImageIO.read(new File(IMAGE2_FiLE));
                image2 = ImageIO.read(new File(IMAGE1_FiLE));
            }

            Effect effect = new Effect(image1, image2, EffectFactory.ScrollRightEffect);

            AnimatedSegue animatedSegue = EffectFactory.provideEffect(effect);

            animatedSegue.addAnimationObserver((animatedSegue1, bufferedImage) -> {
                VideoCreator.animatedImages.add(bufferedImage);
            });
            animatedSegue.addCompletionObserver((animatedSegue1 -> {
                logger.info("Finished");
                full.release();
            }));

            logger.info(Thread.currentThread().getName());

            animatedSegue.start();
            try {
                Thread.sleep(effect.getEffectDuration());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
