package nsu.sber.service.integration;

import lombok.RequiredArgsConstructor;
import nsu.sber.dto.CommandSet;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.model.CommandIntent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackendClient {

    public ProcessingResponse execute(CommandSet commands, String sessionId) {
        // TODO: switch команд и handler для каждой
        return new ProcessingResponse();
    }
}
