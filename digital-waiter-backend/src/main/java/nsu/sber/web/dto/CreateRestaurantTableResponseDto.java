package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class CreateRestaurantTableResponseDto {

    @NotBlank
    @Schema(
            description = ApiConstants.TABLE_NAME_DESCRIPTION,
            example = ApiConstants.TABLE_NAME_EXAMPLE
    )
    private String tableName;

    @NotBlank
    @Schema(
            description = ApiConstants.GUEST_LOGIN_DESCRIPTION,
            example = ApiConstants.LOGIN_EXAMPLE
    )
    private String login;

    @NotBlank
    @Schema(
            description = ApiConstants.PASSWORD_DESCRIPTION,
            example = ApiConstants.PASSWORD_EXAMPLE
    )
    private String password;

}
