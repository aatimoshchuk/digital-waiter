package nsu.sber.domain.model.order;

import lombok.Builder;
import lombok.Data;
import nsu.sber.domain.model.cart.CartResponse;

import java.util.List;

@Data
@Builder
public class CreateOrderRequest {
    private String organizationId;
    private String terminalGroupId;
    private Order order;

    @Data
    @Builder
    public static class Order {
        private List<String> tableIds;
        private CartResponse cart;
    }
}
