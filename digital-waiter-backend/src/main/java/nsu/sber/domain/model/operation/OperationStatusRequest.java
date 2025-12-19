package nsu.sber.domain.model.operation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationStatusRequest {
    private String posOrganizationId;
    private String correlationId;
    private Integer organizationId;
    private String apiKeyEncrypted;
}
