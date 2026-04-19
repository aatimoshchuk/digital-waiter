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

    @NotNull(message = "Field 'paymentType' cannot be null")
    @Schema(example = "CASH", description = "Payment type (can be CASH, QR or CARD)")
    private PaymentType paymentType;

    @NotNull(message = "Field 'isSplitBetweenGuests' cannot be null")
    @Schema(example = "true", description = "Means whether the check should be split between guests.")
    private Boolean isSplitBetweenGuests;

    public enum PaymentType {
        CASH,
        CARD,
        QR
    }

}
