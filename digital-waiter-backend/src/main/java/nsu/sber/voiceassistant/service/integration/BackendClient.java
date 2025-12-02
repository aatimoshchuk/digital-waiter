package nsu.sber.voiceassistant.service.integration;

import lombok.RequiredArgsConstructor;

import nsu.sber.voiceassistant.dto.CommandSet;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackendClient {

    public ProcessingResponse execute(CommandSet commands, String sessionId) {
        // TODO: switch команд и handler для каждой
        return new ProcessingResponse();
    }
}
