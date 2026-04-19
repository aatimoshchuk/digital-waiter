package nsu.sber.web.mapper;

import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import nsu.sber.domain.model.payment.ChoosePaymentTypeResponse;
import nsu.sber.web.dto.ChoosePaymentTypeRequestDto;
import nsu.sber.web.dto.ChoosePaymentTypeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface PaymentDtoMapper {

    ChoosePaymentTypeRequest dtoToChoosePaymentTypeRequest(ChoosePaymentTypeRequestDto choosePaymentTypeRequestDto);

    ChoosePaymentTypeResponseDto choosePaymentTypeResponseToDto(ChoosePaymentTypeResponse choosePaymentTypeResponse);

}
