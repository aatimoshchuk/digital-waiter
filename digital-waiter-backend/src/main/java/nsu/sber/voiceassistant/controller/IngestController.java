package nsu.sber.voiceassistant.controller;

import lombok.RequiredArgsConstructor;
import nsu.sber.voiceassistant.dto.ProcessingRequest;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.ProcessingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/voice")
@RequiredArgsConstructor
public class IngestController {

    private final ProcessingService processingService;

    @PostMapping(value = "/process-audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProcessingResponse> processAudio(@RequestParam("audio") MultipartFile audioFile) {
        ProcessingRequest request = ProcessingRequest.builder()
                .audioFile(audioFile)
                .build();
        return ResponseEntity.ok(processingService.processAudio(request));
    }

    @PostMapping(value = "/process-text")
    public ResponseEntity<ProcessingResponse> processText(
            @RequestParam("text") String text,
            @RequestParam("context") String context) {
        ProcessingRequest request = ProcessingRequest.builder()
                .text(text)
                .context(context)
                .build();
        return ResponseEntity.ok(processingService.processText(request));
    }
}
