package nsu.sber.messaging.pos.iiko.mapper;

import nsu.sber.domain.model.operation.OperationState;
import nsu.sber.domain.model.operation.OperationStatusRequest;
import nsu.sber.domain.model.operation.OperationStatusResponse;
import nsu.sber.messaging.pos.iiko.dto.OperationStatusRequestDto;
import nsu.sber.messaging.pos.iiko.dto.OperationStatusResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        imports = { OperationState.class }
)
public interface OperationMapper {

    @Mapping(target = "exceptionMessage", source = "exceptionInfo.message")
    @Mapping(target = "exceptionDescription", source = "exceptionInfo.description")
    @Mapping(target = "additionalData", source = "exceptionInfo.additionalData")
    @Mapping(
            target = "state",
            expression = "java(OperationState.fromExternal(operationStatusResponseDto.getState()))"
    )
    OperationStatusResponse dtoToOperationStatusResponse(OperationStatusResponseDto operationStatusResponseDto);

    OperationStatusRequestDto operationStatusRequestToDto(OperationStatusRequest operationStatusRequest);

}
