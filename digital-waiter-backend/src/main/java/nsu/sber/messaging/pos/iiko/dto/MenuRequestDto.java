package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuRequestDto {
    private String externalMenuId;
    private List<String> organizationIds;
}
