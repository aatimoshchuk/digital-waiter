package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentTypesRequestDto {
    private List<String> organizationIds;
}
