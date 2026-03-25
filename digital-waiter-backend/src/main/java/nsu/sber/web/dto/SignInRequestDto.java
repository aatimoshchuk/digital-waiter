package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class SignInRequestDto {

    @NotBlank(message = "Field 'login' cannot be empty")
    @Schema(
            description = "Login for authorization in the guest or administrative interface",
            example = ApiConstants.LOGIN_EXAMPLE
    )
    private String login;

    @NotBlank(message = "Field 'password' cannot be empty")
    @Schema(
            description = ApiConstants.PASSWORD_DESCRIPTION,
            example = ApiConstants.PASSWORD_EXAMPLE
    )
    private String password;
}
