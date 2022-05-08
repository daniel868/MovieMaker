package com.example.amproiect2.servicies;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.datasource.MediaDatasource;
import com.example.amproiect2.entities.MovieArgsDto;
import com.example.amproiect2.entities.VideoRenderDto;
import com.example.amproiect2.video.effects.Effect;
import com.example.amproiect2.video.effects.EffectFactory;
import com.example.amproiect2.video.render.RenderBase;
import com.example.amproiect2.video.render.RenderMovie;
import com.example.amproiect2.video.render.RenderPreview;
import com.example.amproiect2.video.render.RenderFactory;
import com.example.amproiect2.video.scripts.ScriptsRender;
import com.example.amproiect2.video.scripts.VideoScriptArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

@Service
public class VideoService {
    private final MediaDatasource mediaDatasource;
    private byte[] cachedPreview;
    private final ScriptsRender script;

    @Autowired
    public VideoService(MediaDatasource mediaDatasource, ScriptsRender script) {
        this.mediaDatasource = mediaDatasource;
        this.script = script;
        cachedPreview = new byte[0];
    }

    @Async
    public void renderPreview(VideoRenderDto videoRenderDto) throws Exception {
        RenderPreview currentRender = buildPreviewRender(videoRenderDto);

        currentRender.renderMediaContent();

        FileInputStream renderInputStream = new FileInputStream(currentRender.getOutputFilename());
        byte[] renderedPreview = renderInputStream.readAllBytes();

        cachedPreview = CompletableFuture.completedFuture(renderedPreview)
                .get();
    }

    public void renderMovie(MovieArgsDto movieArgsDto) {
//        VideoScriptArgs movieArgs = VideoScriptArgs.builder()
//                .imagesUrl(movieArgsDto.getImagesUrl())
//                .fps(movieArgsDto.getFps())
//                .outputFileName(movieArgsDto.getVideoFileName())
//                .outputFolder(movieArgsDto.getVideoFolderPath())
//                .executionScriptFilePath(ScriptsRender.RENDER_SCRIPT_FILE_PATH)
//                .build();
//
//        String[] command = script.buildScriptCommand(movieArgs);
//
//        CompletableFuture<Process> currentProcess = script.executeMovieRender(command);
//
//        cachedPreview = buildMovieRender(movieArgsDto, currentProcess);
        RenderMovie renderMovie = buildMovieRender();

        cachedPreview = renderMovie.executeRenderMovieScript(movieArgsDto);
    }

    private RenderPreview buildPreviewRender(VideoRenderDto videoRenderDto) throws Exception {
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

        String tempFilePath = String.format("%s.mp4", videoRenderDto.getEffectType());


        return ((RenderPreview) RenderFactory
                .provideRender(RenderFactory.RENDER_PREVIEW, requestedEffect))
                .setOutputFileName(tempFilePath);
    }

    private RenderMovie buildMovieRender() {
        return (RenderMovie) RenderFactory
                .provideRender(RenderFactory.RENDER_MOVIE, script);
    }

    public byte[] getCachedPreview() {
        return cachedPreview;
    }

}
