package nsu.sber.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AckNotificationRequestDto {
    @NotNull(message = "Field 'notifications' cannot be empty")
    @NotEmpty(message = "List 'notifications' cannot be empty")
    private List<AckItem> notifications;

    @Data
    public static class AckItem {
        @NotNull(message = "Field 'id' cannot be empty")
        @Min(value = 1, message = "The value of the 'terminalGroupId' field must be greater than or equal to 1")
        private Long id;

        @NotBlank(message = "Field 'pullToken' cannot be empty")
        private String pullToken;
    }
}
