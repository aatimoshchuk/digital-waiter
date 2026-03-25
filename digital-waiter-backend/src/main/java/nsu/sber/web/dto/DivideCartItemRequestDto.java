package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nsu.sber.util.ApiConstants;

import java.util.List;
@Data
public class DivideCartItemRequestDto {

    @NotBlank(message = "Field 'itemId' cannot be empty")
    @Schema(description = ApiConstants.DISH_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String itemId;

    @Schema(description = ApiConstants.SIZE_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String sizeId;

    @Min(value = 0, message = "The value of the 'currentGuestNumber' field must be non-negative")
    @Schema(description = ApiConstants.GUEST_NUMBER_DESCRIPTION, example = ApiConstants.GUEST_NUMBER_EXAMPLE)
    private Integer currentGuestNumber;

    @NotNull(message = "Field 'finalGuestNumbers' cannot be null")
    @Schema(description = ApiConstants.GUEST_NUMBERS_DESCRIPTION, example = ApiConstants.GUEST_NUMBERS_EXAMPLE)
    private List<@Min(0) Integer> finalGuestNumbers;

}
