package nsu.sber.govno.service.nlu.provider;


import nsu.sber.govno.dto.LlmRequest;
import nsu.sber.govno.dto.LlmResponse;

public interface LlmProvider {
    LlmResponse complete(LlmRequest request);

    boolean isAvailable();
}
