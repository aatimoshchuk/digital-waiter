package nsu.sber.domain.model.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResponse {

    private String correlationId;

    private String orderId;

}
