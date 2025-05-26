package nsu.sber.messaging.pos.iiko.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuRequestDto {

    private String externalMenuId;

    private List<String> organizationIds;

}
