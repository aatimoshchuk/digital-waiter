package nsu.sber.domain.model.order;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetOrderByIdRequest {
    private List<String> organizationIds;
    private List<String> orderIds;
}
