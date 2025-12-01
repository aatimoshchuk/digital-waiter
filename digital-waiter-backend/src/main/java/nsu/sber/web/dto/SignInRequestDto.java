package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequestDto {

    @NotBlank
    @Schema(
            description = "Login for authorization in the guest or administrative interface",
            example = "sparks_1"
    )
    private String login;

    @NotBlank
    @Schema(
            description = "Password associated with the login",
            example = "Qwerty123"
    )
    private String password;
}
