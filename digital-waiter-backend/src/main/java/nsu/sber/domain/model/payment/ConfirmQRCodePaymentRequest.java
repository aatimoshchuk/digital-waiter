package nsu.sber.domain.model.payment;

import lombok.Data;

@Data
public class ConfirmQRCodePaymentRequest {
    private String orderId;
    private Double sum;
}
