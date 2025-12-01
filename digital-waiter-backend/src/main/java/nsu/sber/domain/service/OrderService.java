package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.domain.port.pos.PosOrderPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final PosOrderPort posOrderPort;
    private final CartService cartService;
    private final OperationService operationService;
    private final RequestContextProvider requestContextProvider;

    public String createOrderAsync() {
        if (cartService.isCartEmpty()) {
            throw new DigitalWaiterException.EmptyCartException();
        }

        CreateOrderResponse createOrderResponse = posOrderPort.createOrder(buildCreateOrderRequest());
        String correlationId = createOrderResponse.getCorrelationId();

        operationService.trackOrderStatusAsync(correlationId);

//        cartService.clearCart();

        return correlationId;
    }

    private CreateOrderRequest buildCreateOrderRequest() {
        CreateOrderRequest.Order order = CreateOrderRequest.Order
                .builder()
                .tableIds(Collections.singletonList(requestContextProvider.getTableId()))
                .cart(cartService.getCartResponse())
                .build();

        return CreateOrderRequest
                .builder()
                .terminalGroupId(requestContextProvider.getTerminalGroupId())
                .organizationId(requestContextProvider.getOrganizationId())
                .order(order)
                .build();
    }

}
