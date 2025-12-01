package nsu.sber.govno.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LlmRequest {
    private String prompt;
    private String message;
}
