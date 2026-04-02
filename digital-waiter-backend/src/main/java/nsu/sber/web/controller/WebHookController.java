package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.service.WebHookService;
import nsu.sber.web.dto.BaseWebHookEventDto;
import nsu.sber.web.mapper.WebHookDtoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhooks")
@Tag(name = "WebHook Controller")
public class WebHookController {
    private final WebHookDtoMapper webHookDtoMapper;
    private final WebHookService webHookService;

    @PostMapping
    @Operation(
            summary = "Handle a WebHook notification",
            description = "Handles a WebHook notification (only for iiko)"
    )
    public void handleWebHook(
            @RequestHeader(value = "Authorization", required = true) String token,
            @RequestBody BaseWebHookEventDto eventDto) {
        webHookService.processEvent(webHookDtoMapper.dtoToBaseWebHookEvent(eventDto));
    }
}
