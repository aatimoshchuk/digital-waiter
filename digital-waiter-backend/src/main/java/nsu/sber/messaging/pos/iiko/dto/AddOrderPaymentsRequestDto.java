package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddOrderPaymentsRequestDto {
    private String orderId;
    private String organizationId;
    private List<Payment> payments;

    @Data
    public static class Payment {
        private String paymentTypeKind;
        private Double sum;
        private String paymentTypeId;
    }
}
