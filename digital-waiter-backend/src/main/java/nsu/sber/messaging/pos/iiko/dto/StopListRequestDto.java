package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class StopListRequestDto {
    private List<String> organizationIds;
    private List<String> terminalGroupIds;
}
