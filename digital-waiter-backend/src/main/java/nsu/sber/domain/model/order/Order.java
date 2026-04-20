package nsu.sber.domain.model.order;

import lombok.Data;

@Data
public class Order {
    private String id;
    private String creationStatus;
    private OrderStatus status;
    private double sum;
    private double processedPaymentsSum;
}
