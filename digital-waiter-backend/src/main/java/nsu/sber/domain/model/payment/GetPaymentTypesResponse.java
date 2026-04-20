package nsu.sber.domain.model.payment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPaymentTypesResponse {
        private List<PaymentType> paymentTypes;
}
