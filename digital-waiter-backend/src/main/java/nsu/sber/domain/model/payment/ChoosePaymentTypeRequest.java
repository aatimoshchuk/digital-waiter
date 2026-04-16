package nsu.sber.domain.model.payment;

import lombok.Data;

@Data
public class ChoosePaymentTypeRequest {
    private PaymentType paymentType;
    private Boolean isSplitBetweenGuests;
}
