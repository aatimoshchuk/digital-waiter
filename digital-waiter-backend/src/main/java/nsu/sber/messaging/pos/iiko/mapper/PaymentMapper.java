package nsu.sber.messaging.pos.iiko.mapper;

import nsu.sber.domain.model.payment.AddOrderPaymentsRequest;
import nsu.sber.domain.model.payment.PaymentTypesRequest;
import nsu.sber.domain.model.payment.PaymentTypesResponse;
import nsu.sber.messaging.pos.iiko.dto.AddOrderPaymentsRequestDto;
import nsu.sber.messaging.pos.iiko.dto.PaymentTypesRequestDto;
import nsu.sber.messaging.pos.iiko.dto.PaymentTypesResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentTypesRequestDto paymentTypesRequestToDto(PaymentTypesRequest paymentTypesRequest);

    PaymentTypesResponse dtoToPaymentTypesResponse(PaymentTypesResponseDto paymentTypesResponseDto);

    AddOrderPaymentsRequestDto addOrderPaymentsRequestToDto(AddOrderPaymentsRequest addOrderPaymentsRequest);

}
