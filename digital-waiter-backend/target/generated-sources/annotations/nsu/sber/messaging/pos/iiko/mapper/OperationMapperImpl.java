package nsu.sber.messaging.pos.iiko.mapper;

import javax.annotation.processing.Generated;
import nsu.sber.domain.model.operation.OperationState;
import nsu.sber.domain.model.operation.OperationStatusRequest;
import nsu.sber.domain.model.operation.OperationStatusResponse;
import nsu.sber.messaging.pos.iiko.dto.OperationStatusRequestDto;
import nsu.sber.messaging.pos.iiko.dto.OperationStatusResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T20:45:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class OperationMapperImpl implements OperationMapper {

    @Override
    public OperationStatusResponse dtoToOperationStatusResponse(OperationStatusResponseDto operationStatusResponseDto) {
        if ( operationStatusResponseDto == null ) {
            return null;
        }

        OperationStatusResponse.OperationStatusResponseBuilder operationStatusResponse = OperationStatusResponse.builder();

        operationStatusResponse.exceptionMessage( operationStatusResponseDtoExceptionInfoMessage( operationStatusResponseDto ) );
        operationStatusResponse.exceptionDescription( operationStatusResponseDtoExceptionInfoDescription( operationStatusResponseDto ) );
        operationStatusResponse.additionalData( operationStatusResponseDtoExceptionInfoAdditionalData( operationStatusResponseDto ) );

        operationStatusResponse.state( OperationState.fromExternal(operationStatusResponseDto.getState()) );

        return operationStatusResponse.build();
    }

    @Override
    public OperationStatusRequestDto operationStatusRequestToDto(OperationStatusRequest operationStatusRequest) {
        if ( operationStatusRequest == null ) {
            return null;
        }

        OperationStatusRequestDto operationStatusRequestDto = new OperationStatusRequestDto();

        operationStatusRequestDto.setOrganizationId( operationStatusRequest.getOrganizationId() );
        operationStatusRequestDto.setCorrelationId( operationStatusRequest.getCorrelationId() );

        return operationStatusRequestDto;
    }

    private String operationStatusResponseDtoExceptionInfoMessage(OperationStatusResponseDto operationStatusResponseDto) {
        if ( operationStatusResponseDto == null ) {
            return null;
        }
        OperationStatusResponseDto.ExceptionInfo exceptionInfo = operationStatusResponseDto.getExceptionInfo();
        if ( exceptionInfo == null ) {
            return null;
        }
        String message = exceptionInfo.getMessage();
        if ( message == null ) {
            return null;
        }
        return message;
    }

    private String operationStatusResponseDtoExceptionInfoDescription(OperationStatusResponseDto operationStatusResponseDto) {
        if ( operationStatusResponseDto == null ) {
            return null;
        }
        OperationStatusResponseDto.ExceptionInfo exceptionInfo = operationStatusResponseDto.getExceptionInfo();
        if ( exceptionInfo == null ) {
            return null;
        }
        String description = exceptionInfo.getDescription();
        if ( description == null ) {
            return null;
        }
        return description;
    }

    private String operationStatusResponseDtoExceptionInfoAdditionalData(OperationStatusResponseDto operationStatusResponseDto) {
        if ( operationStatusResponseDto == null ) {
            return null;
        }
        OperationStatusResponseDto.ExceptionInfo exceptionInfo = operationStatusResponseDto.getExceptionInfo();
        if ( exceptionInfo == null ) {
            return null;
        }
        String additionalData = exceptionInfo.getAdditionalData();
        if ( additionalData == null ) {
            return null;
        }
        return additionalData;
    }
}
