package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class CreateOrganizationRequestDto {

    @NotBlank(message = "Field 'name' cannot be empty")
    @Size(min = 4, max = 50, message = "The value of the 'name' field must be 4-50 characters long")
    @Schema(
            description = ApiConstants.ORGANIZATION_NAME_DESCRIPTION,
            example = ApiConstants.ORGANIZATION_NAME_EXAMPLE
    )
    private String name;

    @NotBlank(message = "Field 'posOrganizationId' cannot be empty")
    @Schema(
            description = ApiConstants.POS_ORGANIZATION_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posOrganizationId;

    @NotBlank(message = "Field 'apiKey' cannot be empty")
    @Schema(description = ApiConstants.API_KEY_DESCRIPTION)
    private String apiKey;

}
