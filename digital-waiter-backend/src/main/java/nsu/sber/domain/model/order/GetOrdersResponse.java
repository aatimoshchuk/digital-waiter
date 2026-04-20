package nsu.sber.domain.model.order;

import lombok.Data;

import java.util.List;

@Data
public class GetOrdersResponse {
    private List<Order> orders;
}
