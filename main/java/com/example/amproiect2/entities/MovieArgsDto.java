package com.example.amproiect2.entities;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class MovieArgsDto {
    private final String videoFileName;
    private final String videoFolderPath;
    private final String fps;
    private final List<String> imagesUrl;
}
