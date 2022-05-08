package com.example.amproiect2.video.scripts;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

@Service
public class ScriptsRender implements BiConsumer<Process, Throwable> {
    public final static String RENDER_SCRIPT_FILE_PATH = "C:\\Users\\danit\\anaconda3\\Scripts\\someText.bat";

    private byte[] currentMovieArr;
    private String movieFilePath;
    private Semaphore semaphore;


    public CompletableFuture<Process> executeMovieRender(String[] scriptCommand) {
        try {

            Process process = Runtime.getRuntime().exec(scriptCommand);

            return process.onExit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Could not finished successfully rendering the movie");
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

    public byte[] provideCreatedMovie(CompletableFuture<Process> processCompletableFuture,
                                      String filePath,
                                      Semaphore syncSemaphore) {
        movieFilePath = filePath;
        semaphore = syncSemaphore;

        processCompletableFuture.whenComplete(this);
//        try {
//            syncSemaphore.acquire();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return currentMovieArr;
    }

    @Override
    public void accept(Process process, Throwable throwable) {
        try (FileInputStream fileInputStream = new FileInputStream(movieFilePath)) {
            currentMovieArr = fileInputStream.readAllBytes();
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
