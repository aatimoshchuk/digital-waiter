package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetOrdersResponseDto {
    private List<Order> orders;

    @Data
    public static class Order {
        private String id;
        private String creationStatus;
        private OrderInfo order;

        @Data
        public static class OrderInfo {
            private String status;
            private Double sum;
        }
    }
}
