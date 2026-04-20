package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class ConfirmQRCodePaymentRequestDto {

    @NotBlank(message = "Field 'orderId' cannot be null")
    @Schema(example = ApiConstants.POS_ID_EXAMPLE, description = ApiConstants.ORDER_ID_DESCRIPTION)
    private String orderId;

    @NotNull(message = "Field 'sum' cannot be null")
    @Schema(example = "480.0", description = "The sum for which the order was paid")
    private Double sum;
}
