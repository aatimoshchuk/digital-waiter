package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class CreateOrderResponseDto {

    @Schema(description = ApiConstants.CORRELATION_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String correlationId;

    @Schema(description = ApiConstants.ORDER_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String orderId;

}
