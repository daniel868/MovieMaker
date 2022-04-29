package com.example.amproiect2.controllers;

import com.example.amproiect2.servicies.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api/v1/progress")
@CrossOrigin("*")
public class ProgressController {
    private final ProgressService progressService;

    private Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/current-progress")
    public SseEmitter eventEmitter() throws IOException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        UUID guid = UUID.randomUUID();

        sseEmitters.put(guid.toString(), sseEmitter);
        sseEmitter.send(
                SseEmitter.event()
                        .name("GUI_ID")
                        .data(guid)
        );
        sseEmitter.onCompletion(() -> sseEmitters.remove(guid.toString()));
        sseEmitter.onTimeout(() -> sseEmitters.remove(guid.toString()));

        return sseEmitter;
    }

    @PostMapping("/start-progress")
    public ResponseEntity<String> startEvent(@RequestParam("file") MultipartFile file,
                                             @RequestParam("guid") String guid) {
        String message;
        try {
            progressService.generatePercent(sseEmitters.get(guid), guid);
            sseEmitters.remove(guid);
            message = "Start progress bar";
            return ResponseEntity.status(HttpStatus.OK)
                    .body(message);
        } catch (Exception e) {
            message = "Could not start progress bar";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(message);
        }
    }
}
