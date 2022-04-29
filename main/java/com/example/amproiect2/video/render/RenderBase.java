package com.example.amproiect2.video.render;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class RenderBase {
    protected SeekableByteChannel writeOutChannel;
    protected AWTSequenceEncoder encoder;

    protected Logger renderLogger = LoggerFactory.getLogger(RenderBase.class);


    public RenderBase() {
    }

    public void initEncoder(String outputFileName) throws Exception {
        writeOutChannel = NIOUtils.writableFileChannel(outputFileName);
        encoder = new AWTSequenceEncoder(writeOutChannel, Rational.R(25, 1));
    }

    public void closeEncoder() throws Exception {
        encoder.finish();
        NIOUtils.closeQuietly(writeOutChannel);
    }


}
