package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
@AllArgsConstructor
public class JwtAuthenticationDto {

    @NotBlank
    @Schema(
            description = ApiConstants.ACCESS_TOKEN_DESCRIPTION,
            example = ApiConstants.TOKEN_EXAMPLE
    )
    private String accessToken;

    @NotBlank
    @Schema(
            description = ApiConstants.REFRESH_TOKEN_DESCRIPTION,
            example = ApiConstants.TOKEN_EXAMPLE
    )
    private String refreshToken;

}
