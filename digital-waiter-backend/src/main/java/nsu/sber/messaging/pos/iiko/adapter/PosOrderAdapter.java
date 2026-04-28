package nsu.sber.messaging.pos.iiko.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.order.AddOrderItemsRequest;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.domain.model.order.GetOrderByIdRequest;
import nsu.sber.domain.model.order.GetOrdersByTableIdRequest;
import nsu.sber.domain.model.order.GetOrdersResponse;
import nsu.sber.domain.port.pos.PosOrderPort;
import nsu.sber.messaging.pos.iiko.client.IikoClient;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderResponseDto;
import nsu.sber.messaging.pos.iiko.dto.GetOrdersResponseDto;
import nsu.sber.messaging.pos.iiko.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PosOrderAdapter implements PosOrderPort {

    private final OrderMapper orderMapper;
    private final IikoClient iikoClient;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        CreateOrderResponseDto createOrderResponseDto = iikoClient.createOrder(
                null,
                orderMapper.createOrderRequestToDto(createOrderRequest)
        );

        return orderMapper.dtoToCreateOrderResponse(createOrderResponseDto);
    }

    @Override
    public void addOrderItems(AddOrderItemsRequest addOrderItemsRequest) {
        iikoClient.addOrderItems(
                null,
                orderMapper.addOrderItemsRequestToDto(addOrderItemsRequest)
        );
    }

    @Override
    public GetOrdersResponse getOrdersByTableId(GetOrdersByTableIdRequest getOrdersByTableIdRequest) {
        GetOrdersResponseDto responseDto = iikoClient.getOrdersByTableId(
                null,
                orderMapper.getOrdersByTableIdRequestToDto(getOrdersByTableIdRequest)
        );

        return orderMapper.dtoToGetOrdersResponse(responseDto);
    }

    @Override
    public GetOrdersResponse getOrderById(GetOrderByIdRequest getOrderByIdRequest) {
        GetOrdersResponseDto responseDto = iikoClient.getOrderById(
                null,
                orderMapper.getOrderByIdRequestToDto(getOrderByIdRequest)
        );

        return orderMapper.dtoToGetOrdersResponse(responseDto);
    }

}
