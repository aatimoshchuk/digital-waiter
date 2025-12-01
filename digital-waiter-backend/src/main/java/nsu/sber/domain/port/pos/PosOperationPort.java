package nsu.sber.domain.port.pos;

import nsu.sber.domain.model.operation.OperationStatusRequest;
import nsu.sber.domain.model.operation.OperationStatusResponse;

public interface PosOperationPort {

    OperationStatusResponse getOperationStatus(OperationStatusRequest operationStatusRequest);

}
