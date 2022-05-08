package com.example.amproiect2.video.scripts;

import com.amazonaws.services.rekognition.model.Video;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

@Service
public class ScriptsRender {
    public final static String RENDER_SCRIPT_FILE_PATH = "C:\\Users\\danit\\anaconda3\\Scripts\\someText.bat";

    private String movieFilePath;

    private byte[] generatedMovie;

    public byte[] executeMovieRender(String[] scriptCommand, VideoScriptArgs videoScriptArgs) {
        Thread t = new Thread(() -> {
            try {
                Process process = Runtime.getRuntime().exec(scriptCommand);

                long secondsToSleep = (long) (videoScriptArgs.getImagesUrl().size() / 7) * 2700;

                Thread.sleep(secondsToSleep);

                String movieFilePath = String.format("%s%s", videoScriptArgs.getOutputFolder(), videoScriptArgs.getOutputFileName());

                try (FileInputStream fileInputStream = new FileInputStream(movieFilePath)) {
                    generatedMovie = fileInputStream.readAllBytes();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(generatedMovie)
                .orElseThrow(() -> new RuntimeException("Could not generate the movie or read the file"));
    }

    public String[] buildScriptCommand(VideoScriptArgs videoArgs) {

        String[] scriptCommand = new String[videoArgs.getImagesUrl().size() + 7];

        scriptCommand[0] = videoArgs.getCmdExecName();
        scriptCommand[1] = videoArgs.getCmdExecLocation();
        scriptCommand[2] = videoArgs.getCmdExecStartCommand();
        scriptCommand[3] = videoArgs.getExecutionScriptFilePath();
        scriptCommand[4] = videoArgs.getOutputFileName();
        scriptCommand[5] = videoArgs.getOutputFolder();
        scriptCommand[6] = videoArgs.getFps();

        for (int i = 0; i < videoArgs.getImagesUrl().size(); i++) {
            scriptCommand[i + 7] = videoArgs.getImagesUrl()
                    .get(i);
        }

        return scriptCommand;
    }
}
