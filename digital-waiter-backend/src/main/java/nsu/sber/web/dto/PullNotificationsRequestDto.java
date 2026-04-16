package nsu.sber.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PullNotificationsRequestDto {

    @NotBlank(message = "Field 'terminalGroupId' cannot be empty")
    private String terminalGroupId;

}
