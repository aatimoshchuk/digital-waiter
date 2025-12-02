package nsu.sber.voiceassistant.service.nlu.provider;


import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;

public interface LlmProvider {
    LlmResponse complete(LlmRequest request);

    boolean isAvailable();
}
