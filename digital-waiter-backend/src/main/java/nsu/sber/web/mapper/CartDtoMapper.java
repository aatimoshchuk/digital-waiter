package nsu.sber.web.mapper;

import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.cart.ModifyCartItemRequest;
import nsu.sber.web.dto.CartResponseDto;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CartDtoMapper {

    ModifyCartItemRequest dtoToModifyCartItemRequest(ModifyCartItemRequestDto modifyCartItemRequestDto);

    CartResponseDto cartResponseToDto(CartResponse cartResponse);

}
