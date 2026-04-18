package nsu.sber.messaging.pos.iiko.mapper;

import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.order.AddOrderItemsRequest;
import nsu.sber.domain.model.order.AddOrderItemsResponse;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.domain.model.order.GetOrderByIdRequest;
import nsu.sber.domain.model.order.GetOrdersByTableIdRequest;
import nsu.sber.domain.model.order.GetOrdersResponse;
import nsu.sber.domain.model.order.OrderStatus;
import nsu.sber.messaging.pos.iiko.dto.AddOrderItemsRequestDto;
import nsu.sber.messaging.pos.iiko.dto.AddOrderItemsResponseDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderRequestDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderResponseDto;
import nsu.sber.messaging.pos.iiko.dto.GetOrderByIdRequestDto;
import nsu.sber.messaging.pos.iiko.dto.GetOrdersByTableIdRequestDto;
import nsu.sber.messaging.pos.iiko.dto.GetOrdersResponseDto;
import nsu.sber.messaging.pos.iiko.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

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
    @Mapping(
            target = "comment",
            expression = "java(item.getGuestNumber() == 0 ? \"Гость не определен\" : \"Гость \" + item.getGuestNumber())"
    )
    OrderItemDto cartItemToOrderItem(CartResponse.CartItemResponse item);

    @Mapping(target = "statuses", source = "statuses")
    GetOrdersByTableIdRequestDto getOrdersByTableIdRequestToDto(GetOrdersByTableIdRequest request);

    GetOrderByIdRequestDto getOrderByIdRequestToDto(GetOrderByIdRequest request);


    GetOrdersResponse dtoToGetOrdersResponse(GetOrdersResponseDto responseDto);

    default List<String> mapStatuses(List<OrderStatus> statuses) {
        if (statuses == null) {
            return new ArrayList<>();
        }
        return statuses.stream()
                .map(Object::toString)
                .toList();
    }

    default OrderStatus map(String value) {
        return OrderStatus.fromExternal(value);
    }

}
