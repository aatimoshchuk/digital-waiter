package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nsu.sber.util.ApiConstants;

@Data
public class ChoosePaymentTypeRequestDto {

    @NotBlank(message = "Field 'orderId' cannot be null")
    @Schema(example = ApiConstants.POS_ID_EXAMPLE, description = ApiConstants.ORDER_ID_DESCRIPTION)
    private String orderId;

    @NotBlank(message = "Field 'paymentTypeCode' cannot be null")
    @Schema(
            example = ApiConstants.PAYMENT_TYPE_CODE_EXAMPLE,
            description = ApiConstants.PAYMENT_TYPE_CODE_DESCRIPTION + " (can be obtained from GET /payment/type)"
    )
    private String paymentTypeCode;

    @NotBlank(message = "Field 'paymentTypeName' cannot be null")
    @Schema(
            example = ApiConstants.PAYMENT_TYPE_NAME_EXAMPLE,
            description = ApiConstants.PAYMENT_TYPE_NAME_DESCRIPTION
    )
    private String paymentTypeName;

    @NotNull(message = "Field 'isSplitBetweenGuests' cannot be null")
    @Schema(example = "true", description = "Means whether the check should be split between guests.")
    private Boolean isSplitBetweenGuests;

}
