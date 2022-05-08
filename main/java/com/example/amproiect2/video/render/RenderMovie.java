package com.example.amproiect2.video.render;

import com.example.amproiect2.entities.MovieArgsDto;
import com.example.amproiect2.video.scripts.ScriptsRender;
import com.example.amproiect2.video.scripts.VideoScriptArgs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class RenderMovie extends RenderBase {
    private final ScriptsRender scriptsRender;

    public RenderMovie(ScriptsRender scriptsRender) {
        this.scriptsRender = scriptsRender;
    }

    public byte[] executeRenderMovieScript(MovieArgsDto movieArgsDto) {

        VideoScriptArgs args = buildScriptsArgs(movieArgsDto);

        String[] command = scriptsRender.buildScriptCommand(args);

        return scriptsRender.executeMovieRender(command, args);
    }

    private VideoScriptArgs buildScriptsArgs(MovieArgsDto movieArgsDto) {
        return VideoScriptArgs.builder()
                .imagesUrl(movieArgsDto.getImagesUrl())
                .fps(movieArgsDto.getFps())
                .outputFileName(movieArgsDto.getVideoFileName())
                .outputFolder(movieArgsDto.getVideoFolderPath())
                .executionScriptFilePath(ScriptsRender.RENDER_SCRIPT_FILE_PATH)
                .build();
    }

    private void emitProgressStatus(int progressValue) throws Exception {
        sseEmitterLocal.send(SseEmitter.event()
                .name(guidLocal)
                .data(progressValue));
    }
}
