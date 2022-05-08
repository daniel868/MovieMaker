package com.example.amproiect2.video.scripts;

import org.springframework.stereotype.Service;

@Service
public class ScriptsRender {
    public final static String RENDER_SCRIPT_FILE_PATH = "C:\\Users\\danit\\anaconda3\\Scripts\\someText.bat";

    public byte[] executeMovieRender(String[] scriptCommand, VideoScriptArgs videoScriptArgs) {
        Thread executionThread = new MovieRenderThread(scriptCommand, videoScriptArgs);

        executionThread.start();

        try {
            executionThread.join();
            return ((MovieRenderThread) executionThread).getGeneratedMovie();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
