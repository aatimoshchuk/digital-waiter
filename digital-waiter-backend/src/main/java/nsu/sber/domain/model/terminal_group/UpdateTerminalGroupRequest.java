package nsu.sber.domain.model.terminal_group;

import lombok.Data;

@Data
public class UpdateTerminalGroupRequest {

    private String name;

    private String posExternalMenuId;

    private String posTerminalGroupId;

    private Integer organizationId;

}
