package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.order.AddOrderItemsRequest;
import nsu.sber.domain.model.order.AddOrderItemsResponse;
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
    private final RestaurantTableService restaurantTableService;
    private final TerminalGroupService terminalGroupService;
    private final OrganizationService organizationService;
    private final OperationService operationService;

    public CreateOrderResponse createOrderAsync() {
        if (cartService.isCartEmpty()) {
            throw new DigitalWaiterException.EmptyCartException();
        }

        CreateOrderResponse createOrderResponse = posOrderPort.createOrder(buildCreateOrderRequest());
        operationService.trackOrderStatusAsync(createOrderResponse.getCorrelationId());

        return createOrderResponse;
    }

    public AddOrderItemsResponse addOrderItemsAsync(String orderId) {
        if (cartService.isCartEmpty()) {
            throw new DigitalWaiterException.EmptyCartException();
        }

        AddOrderItemsResponse addOrderItemsResponse = posOrderPort.addOrderItems(buildAddOrderItemsRequest(orderId));
        operationService.trackOrderStatusAsync(addOrderItemsResponse.getCorrelationId());

        return addOrderItemsResponse;
    }

    private CreateOrderRequest buildCreateOrderRequest() {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();
        TerminalGroup terminalGroup = terminalGroupService.getTerminalGroup(restaurantTable.getTerminalGroupId());
        Organization organization = organizationService.getOrganization(terminalGroup.getOrganizationId());

        CreateOrderRequest.Order order = CreateOrderRequest.Order
                .builder()
                .tableIds(Collections.singletonList(restaurantTable.getPosTableId()))
                .cart(cartService.getCartResponse())
                .build();

        return CreateOrderRequest
                .builder()
                .terminalGroupId(terminalGroup.getPosTerminalGroupId())
                .organizationId(organization.getPosOrganizationId())
                .order(order)
                .build();
    }

    private AddOrderItemsRequest buildAddOrderItemsRequest(String orderId) {
        Organization organization = organizationService.getCurrentOrganization();

        return AddOrderItemsRequest
                .builder()
                .orderId(orderId)
                .organizationId(organization.getPosOrganizationId())
                .cart(cartService.getCartResponse())
                .build();
    }

}
