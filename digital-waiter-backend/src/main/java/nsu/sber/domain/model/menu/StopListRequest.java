package nsu.sber.domain.model.menu;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StopListRequest {
    private List<String> organizationIds;
    private List<String> terminalGroupIds;
}
