package nsu.sber.domain.model.webhooks;

import lombok.Data;

@Data
public class TableOrderUpdateEventInfo {
    private String id;
    private String organizationId;
    private Integer timestamp;
    private String creationStatus;
}
