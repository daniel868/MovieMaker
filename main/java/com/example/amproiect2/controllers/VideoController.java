package com.example.amproiect2.controllers;

import com.example.amproiect2.entities.VideoRenderDto;
import com.example.amproiect2.servicies.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/render")
@CrossOrigin("*")
public class VideoController {
    private final VideoService service;

    @Autowired
    public VideoController(VideoService service) {
        this.service = service;
    }

    @PostMapping(path = "/preview-upload")
    public void requestPreview(@RequestBody VideoRenderDto videoRenderDto) {
        System.out.println(videoRenderDto);
        try {
            service.renderPreview(videoRenderDto);
        } catch (Exception e) {
            throw new RuntimeException("Could not start render file: " + e.getMessage());
        }
    }

    @GetMapping(path = "/preview-download")
    public byte[] renderPreview() {
        return Optional.of(service.getCachedPreview())
                .orElse(new byte[0]);
    }

    @GetMapping(path = "/preview-percent")
    public int renderStatusPercent() {
        return 0;
    }

}
