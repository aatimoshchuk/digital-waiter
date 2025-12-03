package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class UpdateTerminalGroupRequestDto {

    @Size(min = 5, max = 100, message = "Name must be between 4 and 50 characters")
    @Schema(
            description = ApiConstants.TERMINAL_GROUP_NAME_DESCRIPTION,
            example = ApiConstants.TERMINAL_GROUP_NAME_EXAMPLE
    )
    private String name;

    @Min(1)
    @Schema(
            description = ApiConstants.ORGANIZATION_ID_DESCRIPTION,
            example = ApiConstants.ORGANIZATION_ID_EXAMPLE
    )
    private Integer organizationId;

    @Schema(
            description = ApiConstants.POS_TERMINAL_GROUP_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posTerminalGroupId;

    @Schema(
            description = ApiConstants.POS_EXTERNAL_MENU_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posExternalMenuId;

}
