package nsu.sber.messaging.pos.iiko.mapper;

import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.order.AddOrderItemsRequest;
import nsu.sber.domain.model.order.AddOrderItemsResponse;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.messaging.pos.iiko.dto.AddOrderItemsRequestDto;
import nsu.sber.messaging.pos.iiko.dto.AddOrderItemsResponseDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderRequestDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderResponseDto;
import nsu.sber.messaging.pos.iiko.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    CreateOrderRequestDto createOrderRequestToDto(CreateOrderRequest createOrderRequest);

    @Mapping(target = "items", source = "cart.cartItemResponseList")
    AddOrderItemsRequestDto addOrderItemsRequestToDto(AddOrderItemsRequest addOrderItemsRequest);

    @Mapping(target = "orderId", source = "orderInfo.id")
    CreateOrderResponse dtoToCreateOrderResponse(CreateOrderResponseDto createOrderResponseDto);

    AddOrderItemsResponse dtoToAddOrderItemsResponse(AddOrderItemsResponseDto addOrderItemsResponseDto);

    @Mapping(target = "items", source = "cart.cartItemResponseList")
    CreateOrderRequestDto.Order cartResponseToOrder(CreateOrderRequest.Order order);

    @Mapping(target = "productId", source = "itemId")
    @Mapping(target = "amount", source = "quantity")
    @Mapping(target = "productSizeId", source = "sizeId")
    @Mapping(target = "type", constant = "Product")
    OrderItemDto cartItemToOrderItem(CartResponse.CartItemResponse item);

}
