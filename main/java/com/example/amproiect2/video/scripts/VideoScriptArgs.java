package com.example.amproiect2.video.scripts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class VideoScriptArgs {
    private final static String RENDER_SCRIPT_FILE_PATH = "C:\\Users\\danit\\anaconda3\\Scripts\\someText.bat";

    @Builder.Default
    private String cmdExecName = "cmd.exe";
    @Builder.Default
    private String cmdExecLocation = "/C";
    @Builder.Default
    private String cmdExecStartCommand = "Start";
    @Builder.Default
    private String outputFileName = "out.avi";
    @Builder.Default
    private String outputFolder = "";
    @Builder.Default
    private String fps = "15";

    private List<String> imagesUrl;
    private String executionScriptFilePath;

}
