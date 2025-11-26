package nsu.sber.service.nlu;

import nsu.sber.dto.LlmRequest;
import nsu.sber.dto.LlmResponse;

import java.util.concurrent.CompletableFuture;

public interface LlmProvider {
    LlmResponse complete(LlmRequest request);

    CompletableFuture<LlmResponse> completeAsync(LlmRequest request);

    boolean isAvailable();
}
