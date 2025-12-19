package nsu.sber.messaging.pos.iiko.adapter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.order.AddOrderItemsRequest;
import nsu.sber.domain.model.order.AddOrderItemsResponse;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.domain.port.pos.PosOrderPort;
import nsu.sber.exception.DigitalWaiterException;
import nsu.sber.messaging.pos.iiko.client.IikoClient;
import nsu.sber.messaging.pos.iiko.dto.AddOrderItemsResponseDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderResponseDto;
import nsu.sber.messaging.pos.iiko.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PosOrderAdapter implements PosOrderPort {

    private final OrderMapper orderMapper;
    private final IikoClient iikoClient;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        try {
            CreateOrderResponseDto createOrderResponseDto = iikoClient.createOrder(
                    null,
                    orderMapper.createOrderRequestToDto(createOrderRequest)
            );

            return orderMapper.dtoToCreateOrderResponse(createOrderResponseDto);
        } catch (FeignException e) {
            throw new DigitalWaiterException.OrderCreationException(e.getMessage());
        }
    }

    @Override
    public AddOrderItemsResponse addOrderItems(AddOrderItemsRequest addOrderItemsRequest) {
        try {
            AddOrderItemsResponseDto addOrderItemsResponseDto = iikoClient.addOrderItems(
                    null,
                    orderMapper.addOrderItemsRequestToDto(addOrderItemsRequest)
            );

            return orderMapper.dtoToAddOrderItemsResponse(addOrderItemsResponseDto);
        } catch (FeignException e) {
            throw new DigitalWaiterException.OrderItemsAddingException(e.getMessage());
        }
    }

}
