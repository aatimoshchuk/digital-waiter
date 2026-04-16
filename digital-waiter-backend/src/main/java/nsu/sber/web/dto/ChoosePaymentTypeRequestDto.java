package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChoosePaymentTypeRequestDto {

    @NotNull(message = "Field 'paymentType' cannot be null")
    @Schema(example = "CASH", description = "Payment type (can be CASH or CARD)")
    private PaymentType paymentType;

    @NotNull(message = "Field 'isSplitBetweenGuests' cannot be null")
    @Schema(example = "true", description = "Means whether the check should be split between guests.")
    private Boolean isSplitBetweenGuests;

    public enum PaymentType {
        CASH,
        CARD
    }

}
