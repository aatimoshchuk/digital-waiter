package nsu.sber.domain.model.webhooks;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class BaseWebHookEvent {
    private WebHookEventType eventType;
    private String eventTime;
    private String organizationId;
    private String correlationId;
    private JsonNode eventInfo;
}
