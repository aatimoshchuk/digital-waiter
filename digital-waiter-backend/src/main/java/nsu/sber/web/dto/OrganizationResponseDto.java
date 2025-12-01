package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class OrganizationResponseDto {

    @NotBlank
    @Schema(
            description = ApiConstants.ORGANIZATION_ID_DESCRIPTION,
            example = ApiConstants.ORGANIZATION_ID_EXAMPLE
    )
    private Integer id;

    @NotBlank
    @Schema(
            description = ApiConstants.ORGANIZATION_NAME_DESCRIPTION,
            example = ApiConstants.ORGANIZATION_NAME_EXAMPLE
    )
    private String name;

    @NotBlank
    @Schema(
            description = ApiConstants.POS_ORGANIZATION_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posOrganizationId;

}
