package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class RestaurantTableResponseDto {

    @NotNull
    @Schema(
            description = ApiConstants.TABLE_ID_DESCRIPTION,
            example = ApiConstants.TABLE_ID_EXAMPLE
    )
    private Integer id;

    @NotBlank
    @Schema(
            description = ApiConstants.TABLE_NAME_DESCRIPTION,
            example = ApiConstants.TABLE_NAME_EXAMPLE
    )
    private String name;

    @NotBlank
    @Schema(
            description = ApiConstants.POS_TABLE_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posTableId;

    @NotNull
    @Schema(
            description = ApiConstants.TABLE_TERMINAL_GROUP_ID_DESCRIPTION,
            example = ApiConstants.TERMINAL_GROUP_ID_EXAMPLE
    )
    private Integer terminalGroupId;

    @NotBlank
    @Schema(
            description = ApiConstants.GUEST_LOGIN_DESCRIPTION,
            example = ApiConstants.LOGIN_EXAMPLE
    )
    private String login;

}
