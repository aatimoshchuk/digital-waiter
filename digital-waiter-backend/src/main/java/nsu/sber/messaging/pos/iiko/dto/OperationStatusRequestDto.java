package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

@Data
public class OperationStatusRequestDto {
    private String organizationId;
    private String correlationId;
}
