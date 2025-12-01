package nsu.sber.govno.service.integration;

import lombok.RequiredArgsConstructor;

import nsu.sber.govno.dto.CommandSet;
import nsu.sber.govno.dto.ProcessingResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackendClient {

    public ProcessingResponse execute(CommandSet commands, String sessionId) {
        // TODO: switch команд и handler для каждой
        return new ProcessingResponse();
    }
}
