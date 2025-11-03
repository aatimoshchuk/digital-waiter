package nsu.sber.controller;

import lombok.RequiredArgsConstructor;
import nsu.sber.service.ProcessingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/voice")
@RequiredArgsConstructor
public class IngestController {

    private final ProcessingService processingService;

    // POST /process-audio
}
