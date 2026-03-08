package nsu.sber.messaging.pos.iiko.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderRequestDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-03T12:15:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public CreateOrderRequestDto createOrderRequestToDto(CreateOrderRequest createOrderRequest) {
        if ( createOrderRequest == null ) {
            return null;
        }

        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto();

        createOrderRequestDto.setOrganizationId( createOrderRequest.getOrganizationId() );
        createOrderRequestDto.setTerminalGroupId( createOrderRequest.getTerminalGroupId() );
        createOrderRequestDto.setOrder( cartResponseToOrder( createOrderRequest.getOrder() ) );

        return createOrderRequestDto;
    }

    @Override
    public CreateOrderResponse dtoToCreateOrderResponse(CreateOrderResponseDto createOrderResponseDto) {
        if ( createOrderResponseDto == null ) {
            return null;
        }

        CreateOrderResponse.CreateOrderResponseBuilder createOrderResponse = CreateOrderResponse.builder();

        createOrderResponse.orderId( createOrderResponseDtoOrderInfoId( createOrderResponseDto ) );
        createOrderResponse.correlationId( createOrderResponseDto.getCorrelationId() );

        return createOrderResponse.build();
    }

    @Override
    public CreateOrderRequestDto.Order cartResponseToOrder(CreateOrderRequest.Order order) {
        if ( order == null ) {
            return null;
        }

        CreateOrderRequestDto.Order order1 = new CreateOrderRequestDto.Order();

        List<CartResponse.CartItemResponse> cartItemResponseList = orderCartCartItemResponseList( order );
        order1.setItems( cartItemResponseListToItemList( cartItemResponseList ) );
        List<String> list1 = order.getTableIds();
        if ( list1 != null ) {
            order1.setTableIds( new ArrayList<String>( list1 ) );
        }

        return order1;
    }

    @Override
    public CreateOrderRequestDto.Item cartItemToOrderItem(CartResponse.CartItemResponse item) {
        if ( item == null ) {
            return null;
        }

        CreateOrderRequestDto.Item item1 = new CreateOrderRequestDto.Item();

        item1.setProductId( item.getItemId() );
        item1.setAmount( item.getQuantity() );
        item1.setProductSizeId( item.getSizeId() );
        item1.setPrice( item.getPrice() );

        item1.setType( "Product" );

        return item1;
    }

    private String createOrderResponseDtoOrderInfoId(CreateOrderResponseDto createOrderResponseDto) {
        if ( createOrderResponseDto == null ) {
            return null;
        }
        CreateOrderResponseDto.OrderInfo orderInfo = createOrderResponseDto.getOrderInfo();
        if ( orderInfo == null ) {
            return null;
        }
        String id = orderInfo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private List<CartResponse.CartItemResponse> orderCartCartItemResponseList(CreateOrderRequest.Order order) {
        if ( order == null ) {
            return null;
        }
        CartResponse cart = order.getCart();
        if ( cart == null ) {
            return null;
        }
        List<CartResponse.CartItemResponse> cartItemResponseList = cart.getCartItemResponseList();
        if ( cartItemResponseList == null ) {
            return null;
        }
        return cartItemResponseList;
    }

    protected List<CreateOrderRequestDto.Item> cartItemResponseListToItemList(List<CartResponse.CartItemResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<CreateOrderRequestDto.Item> list1 = new ArrayList<CreateOrderRequestDto.Item>( list.size() );
        for ( CartResponse.CartItemResponse cartItemResponse : list ) {
            list1.add( cartItemToOrderItem( cartItemResponse ) );
        }

        return list1;
    }
}
