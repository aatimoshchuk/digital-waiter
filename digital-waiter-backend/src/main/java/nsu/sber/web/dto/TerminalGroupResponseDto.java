package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class TerminalGroupResponseDto {

    @NotNull
    @Schema(
            description = ApiConstants.TERMINAL_GROUP_ID_DESCRIPTION,
            example = ApiConstants.TERMINAL_GROUP_ID_EXAMPLE
    )
    private Integer id;

    @NotBlank
    @Schema(
            description = ApiConstants.TERMINAL_GROUP_NAME_DESCRIPTION,
            example = ApiConstants.TERMINAL_GROUP_NAME_EXAMPLE
    )
    private String name;

    @NotNull
    @Schema(
            description = ApiConstants.ORGANIZATION_ID_DESCRIPTION,
            example = ApiConstants.ORGANIZATION_ID_EXAMPLE
    )
    private Integer organizationId;

    @NotBlank
    @Schema(
            description = ApiConstants.POS_TERMINAL_GROUP_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posTerminalGroupId;

    @NotBlank
    @Schema(
            description = ApiConstants.POS_EXTERNAL_MENU_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posExternalMenuId;
}
