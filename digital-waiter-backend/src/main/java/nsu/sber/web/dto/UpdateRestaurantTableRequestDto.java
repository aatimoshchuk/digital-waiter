package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nsu.sber.util.ApiConstants;
import nsu.sber.validation.ValidLogin;
import nsu.sber.validation.ValidPassword;

@Data
public class UpdateRestaurantTableRequestDto {

    @Size(min = 1, max = 15, message = "The value of the 'name' field must be 1-15 characters long")
    @Schema(
            description = ApiConstants.TABLE_NAME_DESCRIPTION,
            example = ApiConstants.TABLE_NAME_EXAMPLE
    )
    private String name;

    @Schema(
            description = ApiConstants.POS_TABLE_ID_DESCRIPTION,
            example = ApiConstants.POS_ID_EXAMPLE
    )
    private String posTableId;

    @Min(value = 1, message = "The value of the 'terminalGroupId' field must be greater than or equal to 1")
    @Schema(
            description = ApiConstants.TABLE_TERMINAL_GROUP_ID_DESCRIPTION,
            example = ApiConstants.TERMINAL_GROUP_ID_EXAMPLE
    )
    private Integer terminalGroupId;

    @ValidLogin
    @Schema(
            description = ApiConstants.GUEST_LOGIN_DESCRIPTION,
            example = ApiConstants.LOGIN_EXAMPLE
    )
    private String login;

    @ValidPassword
    @Schema(
            description = ApiConstants.PASSWORD_DESCRIPTION,
            example = ApiConstants.PASSWORD_EXAMPLE
    )
    private String password;

}
