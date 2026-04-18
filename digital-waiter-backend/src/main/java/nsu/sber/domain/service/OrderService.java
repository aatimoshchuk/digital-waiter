package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.order.AddOrderItemsRequest;
import nsu.sber.domain.model.order.AddOrderItemsResponse;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.domain.model.order.GetOrderByIdRequest;
import nsu.sber.domain.model.order.GetOrdersByTableIdRequest;
import nsu.sber.domain.model.order.GetOrdersResponse;
import nsu.sber.domain.model.order.OrderStatus;
import nsu.sber.domain.port.pos.PosOrderPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

        if (hasOpenOrders()) {
            throw new DigitalWaiterException.OpenOrderAlreadyExistException();
        }

        CreateOrderResponse createOrderResponse = posOrderPort.createOrder(buildCreateOrderRequest());
        operationService.trackOrderStatusAsync(createOrderResponse.getCorrelationId());

        return createOrderResponse;
    }

    public AddOrderItemsResponse addOrderItemsAsync(String orderId) {
        if (cartService.isCartEmpty()) {
            throw new DigitalWaiterException.EmptyCartException();
        }

        if (!isOrderExists(orderId)) {
            throw new DigitalWaiterException.OrderNotFoundException(orderId);
        }

        AddOrderItemsResponse addOrderItemsResponse = posOrderPort.addOrderItems(buildAddOrderItemsRequest(orderId));
        operationService.trackOrderStatusAsync(addOrderItemsResponse.getCorrelationId());

        return addOrderItemsResponse;
    }

    public boolean hasOpenOrders() {
        GetOrdersResponse response = getCurrentTableOrders(
                OrderStatus.NEW,
                LocalDateTime.now().minusHours(24)
        );

        for (GetOrdersResponse.Order order : response.getOrders()) {
            if (order.getCreationStatus().equals("Success")) {
                return true;
            }
        }

        return false;
    }

    public boolean isOrderExists(String orderId) {
        GetOrdersResponse response = posOrderPort.getOrderById(buildGetOrderByIdRequest(orderId));

        for (GetOrdersResponse.Order order : response.getOrders()) {
            if (order.getCreationStatus().equals("Success") && order.getOrder().getStatus() == OrderStatus.NEW) {
                return true;
            }
        }

        return false;
    }

    public GetOrdersResponse getCurrentTableOrders(OrderStatus orderStatus, LocalDateTime dateFrom) {
        return posOrderPort.getOrdersByTableId(buildGetOrdersByTableIdRequest(orderStatus, dateFrom));
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

    private GetOrdersByTableIdRequest buildGetOrdersByTableIdRequest(OrderStatus orderStatus, LocalDateTime dateFrom) {
        return GetOrdersByTableIdRequest
                .builder()
                .organizationIds(List.of(organizationService.getCurrentOrganization().getPosOrganizationId()))
                .tableIds(List.of(restaurantTableService.getCurrentRestaurantTable().getPosTableId()))
                .statuses(List.of(orderStatus))
                .dateFrom(dateFrom)
                .build();
    }

    private GetOrderByIdRequest buildGetOrderByIdRequest(String orderId) {
        return GetOrderByIdRequest
                .builder()
                .organizationIds(List.of(organizationService.getCurrentOrganization().getPosOrganizationId()))
                .orderIds(List.of(orderId))
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
