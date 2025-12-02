package nsu.sber.voiceassistant.service.nlu.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public abstract class AbstractLlmProvider implements LlmProvider {

    @Autowired
    protected ObjectMapper objectMapper;

    @Override
    public LlmResponse complete(LlmRequest request) {
        long startTime = System.currentTimeMillis();
        try {
            validateRequest(request);
            LlmResponse response = sendLlmRequest(request);
            long duration = System.currentTimeMillis() - startTime;
            response.setResponseTimeMs(duration);
            return response;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            return LlmResponse.error(e.getMessage());
        }
    }

    protected abstract LlmResponse sendLlmRequest(LlmRequest request) throws Exception;

    protected void validateRequest(LlmRequest request) {
        if (request.getPrompt() == null && request.getMessage() == null) {
            throw new IllegalArgumentException("Prompt or Message is required");
        }
    }
}