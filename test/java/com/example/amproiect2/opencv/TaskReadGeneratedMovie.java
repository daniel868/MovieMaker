package com.example.amproiect2.opencv;

import com.amazonaws.services.codecommit.model.File;
import com.example.amproiect2.video.scripts.ScriptsRender;
import com.example.amproiect2.video.scripts.VideoScriptArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ThrowsAdvice;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TaskReadGeneratedMovie {

    private ScriptsRender underTest;

    private String[] command;

    private VideoScriptArgs videoScriptArgs;

    @BeforeEach
    void setUp() {
        List<String> imagesTest = asList(
                "http://localhost:8080/api/v1/user-profile/images/download/1",
                "http://localhost:8080/api/v1/user-profile/images/download/2",
                "http://localhost:8080/api/v1/user-profile/images/download/3",
                "http://localhost:8080/api/v1/user-profile/images/download/5",
                "http://localhost:8080/api/v1/user-profile/images/download/6",
                "http://localhost:8080/api/v1/user-profile/images/download/7",
                "http://localhost:8080/api/v1/user-profile/images/download/8",
                "http://localhost:8080/api/v1/user-profile/images/download/9",
                "http://localhost:8080/api/v1/user-profile/images/download/10",
                "http://localhost:8080/api/v1/user-profile/images/download/11",
                "http://localhost:8080/api/v1/user-profile/images/download/12",
                "http://localhost:8080/api/v1/user-profile/images/download/13",
                "http://localhost:8080/api/v1/user-profile/images/download/14",
                "http://localhost:8080/api/v1/user-profile/images/download/15",
                "http://localhost:8080/api/v1/user-profile/images/download/16"
        );

        videoScriptArgs = VideoScriptArgs.builder()
                .imagesUrl(imagesTest)
                .outputFileName("someTest.avi")
                .outputFolder("C:\\Users\\danit\\OneDrive\\Desktop\\")
                .executionScriptFilePath(ScriptsRender.RENDER_SCRIPT_FILE_PATH)
                .fps("30")
                .build();

        underTest = new ScriptsRender();

        command = underTest.buildScriptCommand(videoScriptArgs);
    }

    @Test
    void couldReadGeneratedMovie() throws Exception {
        //CompletableFuture<Process> processCompletableFuture = underTest.executeMovieRender(command);

        //   Process currentProcess = processCompletableFuture.get();


        Semaphore syncSemaphore = new Semaphore(0);

        String movieFilePath = String.format("%s%s", videoScriptArgs.getOutputFolder(), videoScriptArgs.getOutputFileName());

        byte[] expected = new byte[0];

        FileInputStream fileInputStream = new FileInputStream(movieFilePath);

        expected = fileInputStream.readAllBytes();

        //byte[] expected = underTest.provideCreatedMovie(processCompletableFuture, movieFilePath, syncSemaphore);

        assertThat(expected).isNullOrEmpty();
    }

    @Test
    void executeAndWaitFinishedProcesses() throws Exception {

        Thread t = new Thread(() -> {
            try {
                Process process = Runtime.getRuntime().exec(command);

                long secondsToSleep = (long) (videoScriptArgs.getImagesUrl().size() / 7) * 2700;

                Thread.sleep(secondsToSleep);

                String movieFilePath = String.format("%s%s", videoScriptArgs.getOutputFolder(), videoScriptArgs.getOutputFileName());

                try (FileInputStream fileInputStream = new FileInputStream(movieFilePath)) {
                    byte[] result = fileInputStream.readAllBytes();

                    assertThat(result.length).isGreaterThan(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();

        t.join();

        System.out.println("Heap size: " + Runtime.getRuntime().

                totalMemory());
        System.out.println("Heap max size: " + Runtime.getRuntime().

                maxMemory());


    }

    @Test
    void readLargeFilesJava() throws Exception {
        byte[] bytes = Files.readAllBytes(Path.of("C:\\Users\\danit\\OneDrive\\Desktop\\someTest.avi"));
        System.out.println(bytes.length);
        assertThat(bytes).isNotNull();
    }

    @Test
    void couldRenderTheMovie() {
        byte[] expected = underTest.executeMovieRender(command, videoScriptArgs);

        assertThat(expected.length).isGreaterThan(0);
    }
}
