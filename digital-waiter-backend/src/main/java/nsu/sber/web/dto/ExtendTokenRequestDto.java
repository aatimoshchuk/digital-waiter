package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class ExtendTokenRequestDto {

    @NotBlank
    @Schema(
            description = ApiConstants.REFRESH_TOKEN_DESCRIPTION,
            example = ApiConstants.TOKEN_EXAMPLE
    )
    private String refreshToken;

}
