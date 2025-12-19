package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDto {
    private String organizationId;
    private String terminalGroupId;
    private Order order;

    @Data
    public static class Order {
        private List<String> tableIds;
        private List<OrderItemDto> items;
    }
}
