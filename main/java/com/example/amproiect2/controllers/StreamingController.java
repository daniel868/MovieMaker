package com.example.amproiect2.controllers;

import com.example.amproiect2.servicies.StreamingService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/streaming")
@CrossOrigin("*")
public class StreamingController {
    private final StreamingService service;

    public StreamingController(StreamingService service) {
        this.service = service;
    }


    @GetMapping(value = "/video/{title}", produces = "video/mp4")
    public Mono<byte[]> getVideos(@PathVariable String title, @RequestHeader("Range") String range) {
        System.out.println("Range in bytes: " + range);

        return service.getVideoFromByte(title);
    }
}
