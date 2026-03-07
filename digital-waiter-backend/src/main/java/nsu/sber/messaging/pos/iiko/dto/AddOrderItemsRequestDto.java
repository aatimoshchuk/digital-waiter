package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddOrderItemsRequestDto {
    private String orderId;
    private String organizationId;
    private List<OrderItemDto> items;
}
