package nsu.sber.domain.model.order;

import lombok.Data;

import java.util.List;

@Data
public class GetOrdersResponse {
    private List<Order> orders;

    @Data
    public static class Order {
        private String id;
        private String creationStatus;
        private OrderInfo order;

        @Data
        public static class OrderInfo {
            private OrderStatus status;
        }
    }
}
