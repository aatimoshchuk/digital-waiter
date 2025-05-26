package nsu.sber.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TerminalGroup {

    private Integer id;

    private String posExternalMenuId;

    private String posTerminalGroupId;

    private Integer organizationId;

}
