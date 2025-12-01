package nsu.sber.domain.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TerminalGroup {

    private Integer id;

    private String name;

    private String posExternalMenuId;

    private String posTerminalGroupId;

    private Organization organization;

}
