package nsu.sber.domain.model.payment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddOrderPaymentsRequest {
    private String orderId;
    private String organizationId;
    private List<Payment> payments;

    @Data
    @Builder
    public static class Payment {
        private String paymentTypeKind;
        private Double sum;
        private String paymentTypeId;
    }
}
