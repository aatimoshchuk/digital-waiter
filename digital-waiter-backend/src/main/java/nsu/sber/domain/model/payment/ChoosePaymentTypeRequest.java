package nsu.sber.domain.model.payment;

import lombok.Data;

@Data
public class ChoosePaymentTypeRequest {
    private String orderId;
    private String paymentTypeCode;
    private String paymentTypeName;
    private Boolean isSplitBetweenGuests;
}
