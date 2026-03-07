package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
@AllArgsConstructor
public class AddOrderItemsResponseDto {

    @Schema(description = ApiConstants.CORRELATION_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String correlationId;

}
