package nsu.sber.domain.model.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentTypesRequest {
    private List<String> organizationIds;
}
