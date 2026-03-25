package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class CreateRestaurantTableRequestDto {

    @NotBlank(message = "Field 'name' cannot be empty")
    @Size(min = 1, max = 15, message = "The value of the 'name' field must be 1-15 characters long")
    @Schema(
            description = ApiConstants.TABLE_NAME_DESCRIPTION,
            example = ApiConstants.TABLE_NAME_EXAMPLE
    )
    private String name;

    @NotBlank(message = "Field 'posTableId' cannot be empty")
    @Schema(
            description = ApiConstants.POS_TABLE_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posTableId;

    @Min(value = 1, message = "The value of the 'terminalGroupId' field must be greater than or equal to 1")
    @NotNull(message = "Field 'terminalGroupId' cannot be null")
    @Schema(
            description = ApiConstants.TABLE_TERMINAL_GROUP_ID_DESCRIPTION,
            example = ApiConstants.TERMINAL_GROUP_ID_EXAMPLE
    )
    private Integer terminalGroupId;

}
