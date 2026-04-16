package nsu.sber.web.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetNotificationsRequestDto {

    @NotBlank(message = "Field 'terminalGroupId cannot be empty")
    private String terminalGroupId;

}
