package nsu.sber.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.dto.ProcessingRequest;
import nsu.sber.dto.ProcessingResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessingService {

    public ProcessingResponse processAudio(ProcessingRequest request) {
        // 1. Speech to text
        // 2. NLU
        // 3. Parse command
        // 4. Execute command
        // 5. Create response
        return new ProcessingResponse();
    }

}
