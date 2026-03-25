package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class GuestLogoutRequestDto {

    @NotBlank(message = "Field 'password' cannot be empty")
    @Schema(
            description = "Guest user password for confirmation of logout",
            example = ApiConstants.PASSWORD_EXAMPLE
    )
    private String password;

}
