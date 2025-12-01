package nsu.sber.govno;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.govno.dto.ProcessingRequest;
import nsu.sber.govno.dto.ProcessingResponse;
import nsu.sber.govno.service.ProcessingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Tag(name = "Тестовые методы", description = "Эндпоинты для тестирования NLU/Processing")
public class TestController {

    private final ProcessingService processingService;

    @PostMapping("/text")
    @Operation(
            summary = "Тест обработки текста",
            description = "Принимает текст и обрабатывает его через NLU и CommandIntentProcessor"
    )
    public ProcessingResponse testTextProcessing(@RequestBody @Valid ProcessingRequest request) {
        return processingService.processText(request);
    }
}
