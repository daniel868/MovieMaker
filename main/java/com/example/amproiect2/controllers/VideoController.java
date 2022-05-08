package com.example.amproiect2.controllers;

import com.example.amproiect2.entities.MovieArgsDto;
import com.example.amproiect2.entities.VideoRenderDto;
import com.example.amproiect2.servicies.RenderProgressService;
import com.example.amproiect2.servicies.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/render")
@CrossOrigin("*")
public class VideoController {
    private final VideoService service;
    private final RenderProgressService renderProgressService;

    @Autowired
    public VideoController(VideoService service, RenderProgressService renderProgressService) {
        this.service = service;
        this.renderProgressService = renderProgressService;
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

    @PostMapping("/movie-upload")
    public void requestMovieRender(@RequestBody MovieArgsDto movieArgsDto) throws Exception {
        service.renderMovie(movieArgsDto);
    }

    @GetMapping(path = "/preview-download")
    public byte[] renderPreview() {
        return Optional.of(service.getCachedPreview())
                .orElse(new byte[0]);
    }

    @GetMapping(path = "/movie-download")
    public byte[] renderMovie() {
        return Optional.of(service.getCachedMovie())
                .orElse(new byte[0]);
    }

    @GetMapping("/current-progress")
    public SseEmitter provideProgressEvent() throws IOException {
        return renderProgressService.provideEvent();
    }

    @PostMapping("/start-progress")
    public ResponseEntity<String> startProgressEvent(@RequestParam("guid") String guid) {
        return renderProgressService.initEvent(guid);
    }

}
