package com.example.amproiect2.video.tests.providers;

import com.example.amproiect2.video.tests.VideoCreator;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Semaphore;

public class VideoEncoder extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(VideoEncoder.class);

    private final Semaphore full;


    public VideoEncoder(Semaphore full) {
        this.full = full;
    }

    @Override
    public void run() {
        try {
            startEncoding();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startEncoding() throws Exception {
        SeekableByteChannel out = NIOUtils.writableFileChannel("output.mp4");
        AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));

        full.acquire();

        List<BufferedImage> encodeList = VideoCreator.animatedImages;


        for (int i = 0; i < encodeList.size(); i++) {
            int percent = i * 100 / encodeList.size();
            encoder.encodeImage(encodeList.get(i));
            logger.info("Processed: " + percent + "%");
        }

        encoder.finish();
        NIOUtils.closeQuietly(out);
    }
}
