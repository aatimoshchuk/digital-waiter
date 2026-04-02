package nsu.sber.web.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class BaseWebHookEventDto {
    private String eventType;
    private String eventTime;
    private String organizationId;
    private String correlationId;
    private JsonNode eventInfo;
}
