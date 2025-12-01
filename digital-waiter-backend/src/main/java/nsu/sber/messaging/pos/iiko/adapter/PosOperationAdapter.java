package nsu.sber.messaging.pos.iiko.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.operation.OperationStatusRequest;
import nsu.sber.domain.model.operation.OperationStatusResponse;
import nsu.sber.domain.port.pos.PosOperationPort;
import nsu.sber.messaging.pos.iiko.client.IikoClient;
import nsu.sber.messaging.pos.iiko.mapper.OperationMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PosOperationAdapter implements PosOperationPort {

    private final OperationMapper operationMapper;
    private final IikoClient iikoClient;

    @Override
    public OperationStatusResponse getOperationStatus(OperationStatusRequest operationStatusRequest) {
        return operationMapper.dtoToOperationStatusResponse(iikoClient.getOperationStatus(
                null,
                operationMapper.operationStatusRequestToDto(operationStatusRequest)
        ));
    }

}
