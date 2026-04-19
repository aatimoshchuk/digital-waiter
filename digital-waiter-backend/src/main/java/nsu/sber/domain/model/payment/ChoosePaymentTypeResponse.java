package nsu.sber.domain.model.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoosePaymentTypeResponse {
    private String qrCodeUrl;
    private Double sum;
}
