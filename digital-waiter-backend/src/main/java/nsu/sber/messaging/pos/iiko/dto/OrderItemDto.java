package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private String productId;
    private double price;
    private String type;
    private double amount;
    private String productSizeId;
}
