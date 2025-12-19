package nsu.sber.domain.model.order;

import lombok.Builder;
import lombok.Data;
import nsu.sber.domain.model.cart.CartResponse;

@Data
@Builder
public class AddOrderItemsRequest {
    private String orderId;
    private String organizationId;
    private CartResponse cart;
}
