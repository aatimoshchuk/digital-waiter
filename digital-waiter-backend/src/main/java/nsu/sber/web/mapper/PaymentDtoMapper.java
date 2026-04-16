package nsu.sber.web.mapper;

import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import nsu.sber.web.dto.ChoosePaymentTypeRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentDtoMapper {

    ChoosePaymentTypeRequest dtoToChoosePaymentTypeRequest(ChoosePaymentTypeRequestDto choosePaymentTypeRequestDto);

}
