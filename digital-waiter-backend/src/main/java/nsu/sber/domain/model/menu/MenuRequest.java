package nsu.sber.domain.model.menu;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuRequest {
    private String externalMenuId;
    private List<String> organizationIds;
}
