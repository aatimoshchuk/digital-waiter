package nsu.sber.web.mapper;

import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.cart.DivideCartItemRequest;
import nsu.sber.domain.model.cart.ModifyCartItemRequest;
import nsu.sber.domain.model.cart.TransferCartItemRequest;
import nsu.sber.web.dto.CartResponseDto;
import nsu.sber.web.dto.DivideCartItemRequestDto;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import nsu.sber.web.dto.TransferCartItemRequestDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CartDtoMapper {

    ModifyCartItemRequest dtoToModifyCartItemRequest(ModifyCartItemRequestDto modifyCartItemRequestDto);

    DivideCartItemRequest dtoToDivideCartItemRequest(DivideCartItemRequestDto divideCartItemRequestDto);

    CartResponseDto cartResponseToDto(CartResponse cartResponse);

    TransferCartItemRequest dtoToTransferCartItemRequest(TransferCartItemRequestDto transferCartItemRequestDto);

    @AfterMapping
    default void handleEmptyList(@MappingTarget DivideCartItemRequest divideCartItemRequest, DivideCartItemRequestDto divideCartItemRequestDto) {
        if (divideCartItemRequest.getFinalGuestNumbers() == null) {
            divideCartItemRequest.setFinalGuestNumbers(new ArrayList<>());
        }
    }

}
