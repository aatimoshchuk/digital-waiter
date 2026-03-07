package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class UpdateOrganizationRequestDto {

    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 characters")
    @Schema(
            description = ApiConstants.ORGANIZATION_NAME_DESCRIPTION,
            example = ApiConstants.ORGANIZATION_NAME_EXAMPLE
    )
    private String name;

    @Schema(
            description = ApiConstants.POS_ORGANIZATION_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posOrganizationId;

    @Schema(description = ApiConstants.API_KEY_DESCRIPTION)
    private String apiKey;

}
