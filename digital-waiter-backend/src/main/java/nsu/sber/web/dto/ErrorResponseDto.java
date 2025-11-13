package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ErrorResponseDto {

    @Schema(description = "Http request status", example = "400")
    private int status;

    @Schema(description = "Human-readable error message", example = "Invalid username or password")
    private String message;

    @Schema(description = "Time when the error occurred", example = "2025-10-17T14:35:21.123Z")
    private LocalDateTime timestamp;

    @Schema(
            description = "Unique identifier for this error occurrence",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private UUID uuid;
}
