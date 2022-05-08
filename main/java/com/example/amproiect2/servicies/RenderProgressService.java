package com.example.amproiect2.servicies;

import com.example.amproiect2.video.render.RenderPreview;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RenderProgressService {
    private Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public SseEmitter provideEvent() throws IOException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        String eventKey = "1234";

        sseEmitters.put(eventKey, sseEmitter);
        sseEmitter.send(
                SseEmitter.event()
                        .name("GUI_ID")
                        .data(eventKey)
        );
        sseEmitter.onCompletion(() -> sseEmitters.remove(eventKey));
        sseEmitter.onTimeout(() -> sseEmitters.remove(eventKey));

        return sseEmitter;
    }

    public ResponseEntity<String> initEvent(String guid) {
        String message;
        try {
            RenderPreview.initProgressBar(sseEmitters.get(guid), guid);
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
