package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetOrderByIdRequestDto {
    private List<String> organizationIds;
    private List<String> orderIds;
}
