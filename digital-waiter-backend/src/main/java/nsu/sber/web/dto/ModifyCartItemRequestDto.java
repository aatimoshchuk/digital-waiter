package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class ModifyCartItemRequestDto {

    @NotBlank
    @Schema(description = ApiConstants.DISH_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String itemId;

    @Schema(description = ApiConstants.SIZE_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String sizeId;
}
