package nsu.sber.web.mapper;

import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import nsu.sber.domain.model.payment.ChoosePaymentTypeResponse;
import nsu.sber.domain.model.payment.ConfirmQRCodePaymentRequest;
import nsu.sber.domain.model.payment.GetPaymentTypesResponse;
import nsu.sber.web.dto.ChoosePaymentTypeRequestDto;
import nsu.sber.web.dto.ChoosePaymentTypeResponseDto;
import nsu.sber.web.dto.ConfirmQRCodePaymentRequestDto;
import nsu.sber.web.dto.GetPaymentTypesResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

    ChoosePaymentTypeRequest dtoToChoosePaymentTypeRequest(ChoosePaymentTypeRequestDto choosePaymentTypeRequestDto);

    ChoosePaymentTypeResponseDto choosePaymentTypeResponseToDto(ChoosePaymentTypeResponse choosePaymentTypeResponse);

    GetPaymentTypesResponseDto getPaymentTypesResponseToDto(GetPaymentTypesResponse getPaymentTypesResponse);

    ConfirmQRCodePaymentRequest dtoToConfirmQRCodePaymentRequest(ConfirmQRCodePaymentRequestDto requestDto);

}
