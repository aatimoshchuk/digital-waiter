package nsu.sber.domain.model.payment;

import lombok.Data;

import java.util.List;

@Data
public class PaymentTypesResponse {
    private List<PaymentType> paymentTypes;
}
