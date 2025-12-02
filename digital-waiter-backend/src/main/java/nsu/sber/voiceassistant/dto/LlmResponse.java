package nsu.sber.voiceassistant.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LlmResponse {
    private String content;
    boolean success;
    private String error;
    private long responseTimeMs;

    public static LlmResponse error(String message) {
        return LlmResponse.builder()
                .success(false)
                .error(message)
                .build();
    }
}
