package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.RoleType;
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
    private final UserService userService;
    private final RestaurantTableService restaurantTableService;
    private final OperationService operationService;

    public String createOrderAsync() {
        if (userService.getCurrentUser().getRole() != RoleType.GUEST) {
            throw new DigitalWaiterException.InvalidUserRoleException();
        }

        if (cartService.isCartEmpty()) {
            throw new DigitalWaiterException.EmptyCartException();
        }

        CreateOrderResponse createOrderResponse = posOrderPort.createOrder(buildCreateOrderRequest());
        String correlationId = createOrderResponse.getCorrelationId();

        operationService.trackOrderStatusAsync(correlationId);

        return correlationId;
    }

    private CreateOrderRequest buildCreateOrderRequest() {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();

        CreateOrderRequest.Order order = CreateOrderRequest.Order
                .builder()
                .tableIds(Collections.singletonList(restaurantTable.getPosTableId()))
                .cart(cartService.getCartResponse())
                .build();

        return CreateOrderRequest
                .builder()
                .terminalGroupId(restaurantTable.getTerminalGroup().getPosTerminalGroupId())
                .organizationId(restaurantTable.getTerminalGroup().getOrganization().getPosOrganizationId())
                .order(order)
                .build();
    }

}
