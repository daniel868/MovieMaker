package com.example.amproiect2.servicies;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.datasource.MediaDatasource;
import com.example.amproiect2.entities.VideoRenderDto;
import com.example.amproiect2.video.effects.Effect;
import com.example.amproiect2.video.effects.EffectFactory;
import com.example.amproiect2.video.render.Render;
import com.example.amproiect2.video.render.RenderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class VideoService {
    private final MediaDatasource mediaDatasource;

    private byte[] cachedPreview = new byte[0];


    @Autowired
    public VideoService(MediaDatasource mediaDatasource) {
        this.mediaDatasource = mediaDatasource;
    }

    @Async
    public void renderPreview(VideoRenderDto videoRenderDto) throws Exception {
        Render currentRender = buildRender(videoRenderDto);

        currentRender.renderMediaContent();

        FileInputStream renderInputStream = new FileInputStream(currentRender.getOutputFilename());
        byte[] renderedPreview = renderInputStream.readAllBytes();

        cachedPreview = CompletableFuture.completedFuture(renderedPreview)
                .get();
    }

    private Render buildRender(VideoRenderDto videoRenderDto) throws Exception {
        byte[] source = mediaDatasource.getCachedImages()
                .get(videoRenderDto.getSourceIndex());

        byte[] destination = mediaDatasource.getCachedImages()
                .get(videoRenderDto.getDestinationIndex());

        ByteArrayInputStream sourceByteStream = new ByteArrayInputStream(source);
        ByteArrayInputStream destinationByteStream = new ByteArrayInputStream(destination);

        BufferedImage sourceImage = ImageIO.read(sourceByteStream);
        BufferedImage destinationImage = ImageIO.read(destinationByteStream);

        Effect effect = new Effect(sourceImage,
                destinationImage,
                videoRenderDto.getEffectType());

        AnimatedSegue requestedEffect = EffectFactory.provideEffect(effect);

        String outputFileName = String.format("%s.mp4", videoRenderDto.getEffectType());

        return RenderFactory
                .provideRender(RenderFactory.RENDER_PREVIEW, requestedEffect)
                .setOutputFileName(outputFileName);
    }

    public byte[] getCachedPreview() {
        return cachedPreview;
    }

}
