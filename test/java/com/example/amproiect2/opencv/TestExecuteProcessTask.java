package com.example.amproiect2.opencv;

import com.example.amproiect2.video.scripts.ScriptsRender;
import com.example.amproiect2.video.scripts.VideoScriptArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestExecuteProcessTask implements BiConsumer<Process, Throwable> {
    private ScriptsRender underTest;

    private byte[] outputResult;
    Semaphore mySemaphore = new Semaphore(0);

    @BeforeEach
    void setUp() {
        underTest = new ScriptsRender();
    }

    @Test
    void couldExecutePythonScript() {
        List<String> imagesUrls = asList("http://localhost:8080/api/v1/user-profile/images/download/1",
                "http://localhost:8080/api/v1/user-profile/images/download/2",
                "http://localhost:8080/api/v1/user-profile/images/download/3",
                "http://localhost:8080/api/v1/user-profile/images/download/4"
        );
        String fileName = "output.avi";
        String outputFolder = "C:\\Users\\danit\\OneDrive\\Desktop\\";
        String videoFps = "15";

        VideoScriptArgs args = VideoScriptArgs.builder()
                .imagesUrl(imagesUrls)
                .outputFolder(outputFolder)
                .outputFileName(fileName)
                .executionScriptFilePath(ScriptsRender.RENDER_SCRIPT_FILE_PATH)
                .fps(videoFps)
                .build();

        String[] command = underTest.buildScriptCommand(args);

        CompletableFuture<Process> processCompletableFuture = underTest.executeMovieRender(command);
        processCompletableFuture.whenComplete(this);

        try {
            mySemaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(outputResult.length).isGreaterThan(0);
    }

    @Override
    public void accept(Process process, Throwable throwable) {
        try (InputStream inputStream =
                     new FileInputStream("C:\\Users\\danit\\OneDrive\\Desktop\\output.avi")) {
            outputResult = inputStream.readAllBytes();
            mySemaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
