package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInResponseDto {

    @NotNull
    @Schema(description = "JWT authentication")
    private JwtAuthenticationDto jwtAuthenticationDto;

    @NotNull
    @Schema(
            description = "Current user's role",
            example = "ADMIN"
    )
    private String role;

}
