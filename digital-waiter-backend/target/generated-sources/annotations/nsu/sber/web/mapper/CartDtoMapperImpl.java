package nsu.sber.web.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.cart.ModifyCartItemRequest;
import nsu.sber.web.dto.CartResponseDto;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T20:45:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class CartDtoMapperImpl implements CartDtoMapper {

    @Override
    public ModifyCartItemRequest dtoToModifyCartItemRequest(ModifyCartItemRequestDto modifyCartItemRequestDto) {
        if ( modifyCartItemRequestDto == null ) {
            return null;
        }

        ModifyCartItemRequest modifyCartItemRequest = new ModifyCartItemRequest();

        modifyCartItemRequest.setItemId( modifyCartItemRequestDto.getItemId() );
        modifyCartItemRequest.setSizeId( modifyCartItemRequestDto.getSizeId() );

        return modifyCartItemRequest;
    }

    @Override
    public CartResponseDto cartResponseToDto(CartResponse cartResponse) {
        if ( cartResponse == null ) {
            return null;
        }

        CartResponseDto cartResponseDto = new CartResponseDto();

        List<CartResponse.CartItemResponse> list = cartResponse.getCartItemResponseList();
        if ( list != null ) {
            cartResponseDto.setCartItemResponseList( new ArrayList<CartResponse.CartItemResponse>( list ) );
        }

        return cartResponseDto;
    }
}
