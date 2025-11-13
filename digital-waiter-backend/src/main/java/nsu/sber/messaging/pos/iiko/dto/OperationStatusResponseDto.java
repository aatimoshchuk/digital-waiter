package nsu.sber.messaging.pos.iiko.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationStatusResponseDto {
    private String state;
    private ExceptionInfo exceptionInfo;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExceptionInfo {
        private int code;
        private String message;
        private String description;
        private String additionalData;
    }
}
