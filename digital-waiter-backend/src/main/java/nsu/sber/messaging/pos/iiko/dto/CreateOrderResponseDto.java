package nsu.sber.messaging.pos.iiko.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderResponseDto {
    private String correlationId;
    private OrderInfo orderInfo;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderInfo {
        private String id;
    }
}
