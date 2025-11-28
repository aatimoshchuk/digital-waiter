package nsu.sber.service.nlu.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.sber.dto.LlmRequest;
import nsu.sber.dto.LlmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            response.setResponseTimeMs(System.currentTimeMillis() - startTime);
            return response;
        } catch (Exception e) {
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
