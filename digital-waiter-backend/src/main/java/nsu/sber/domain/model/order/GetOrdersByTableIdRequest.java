package nsu.sber.domain.model.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetOrdersByTableIdRequest {
    private List<String> organizationIds;
    private List<String> tableIds;
    private List<OrderStatus> statuses;
    private LocalDateTime dateFrom;
}
