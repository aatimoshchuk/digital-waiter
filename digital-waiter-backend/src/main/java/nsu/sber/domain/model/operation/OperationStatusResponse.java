package nsu.sber.domain.model.operation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationStatusResponse {

    private OperationState state;

    private String exceptionMessage;

    private String exceptionDescription;

    private String additionalData;

}
