package com.example.amproiect2.video.scripts;

import lombok.Getter;

import java.io.FileInputStream;


@Getter
public class MovieRenderThread extends Thread {
    private String[] scriptCommand;
    private VideoScriptArgs videoScriptArgs;

    private byte[] generatedMovie = new byte[0];

    public MovieRenderThread(String[] scriptCommand, VideoScriptArgs videoScriptArgs) {
        this.scriptCommand = scriptCommand;
        this.videoScriptArgs = videoScriptArgs;
    }

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec(scriptCommand);

            long secondsToSleep = (long) 2700 / 7 * (videoScriptArgs.getImagesUrl().size());

            sleep(secondsToSleep);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
