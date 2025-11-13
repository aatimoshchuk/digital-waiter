package nsu.sber.messaging.pos.iiko.mapper;

import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderRequestDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    CreateOrderRequestDto createOrderRequestToDto(CreateOrderRequest createOrderRequest);

    @Mapping(target = "orderId", source = "orderInfo.id")
    CreateOrderResponse dtoToCreateOrderResponse(CreateOrderResponseDto createOrderResponseDto);

    @Mapping(target = "items", source = "cart.cartItemResponseList")
    CreateOrderRequestDto.Order cartResponseToOrder(CreateOrderRequest.Order order);

    @Mapping(target = "productId", source = "itemId")
    @Mapping(target = "amount", source = "quantity")
    @Mapping(target = "productSizeId", source = "sizeId")
    @Mapping(target = "type", constant = "Product")
    CreateOrderRequestDto.Item cartItemToOrderItem(CartResponse.CartItemResponse item);

}
