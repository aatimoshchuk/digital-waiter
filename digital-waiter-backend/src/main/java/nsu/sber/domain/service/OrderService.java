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

        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();
        TerminalGroup terminalGroup = terminalGroupService.getTerminalGroup(restaurantTable.getTerminalGroupId());
        Organization organization = organizationService.getOrganization(terminalGroup.getOrganizationId());

        if (hasOpenOrders(organization.getPosOrganizationId(), terminalGroup.getPosTerminalGroupId())) {
            throw new DigitalWaiterException.OpenOrderAlreadyExistException();
        }

        CreateOrderResponse createOrderResponse = posOrderPort.createOrder(buildCreateOrderRequest(
                restaurantTable.getPosTableId(),
                terminalGroup.getPosTerminalGroupId(),
                organization.getPosOrganizationId()
        ));
        operationService.trackOrderStatusAsync(createOrderResponse.getCorrelationId());

        return createOrderResponse;
    }

    public AddOrderItemsResponse addOrderItemsAsync(String orderId) {
        if (cartService.isCartEmpty()) {
            throw new DigitalWaiterException.EmptyCartException();
        }

        String posOrganizationId = organizationService.getCurrentOrganization().getPosOrganizationId();

        if (!isOrderExists(posOrganizationId, orderId)) {
            throw new DigitalWaiterException.OrderNotFoundException(orderId);
        }

        AddOrderItemsResponse addOrderItemsResponse = posOrderPort.addOrderItems(
                buildAddOrderItemsRequest(posOrganizationId, orderId)
        );
        operationService.trackOrderStatusAsync(addOrderItemsResponse.getCorrelationId());

        return addOrderItemsResponse;
    }

    public boolean hasOpenOrders(String posOrganizationId, String posTableId) {
        GetOrdersResponse response = getCurrentTableOrders(
                posOrganizationId,
                posTableId,
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

    public boolean isOrderExists(String posOrganizationId, String orderId) {
        GetOrdersResponse response = posOrderPort.getOrderById(buildGetOrderByIdRequest(posOrganizationId, orderId));

        for (GetOrdersResponse.Order order : response.getOrders()) {
            if (order.getCreationStatus().equals("Success") && order.getOrder().getStatus() == OrderStatus.NEW) {
                return true;
            }
        }

        return false;
    }

    public GetOrdersResponse getOrderById(String posOrganizationId, String orderId) {
        return posOrderPort.getOrderById(buildGetOrderByIdRequest(posOrganizationId, orderId));
    }

    public GetOrdersResponse getCurrentTableOrders(
            String posOrganizationId,
            String posTableId,
            OrderStatus orderStatus,
            LocalDateTime dateFrom
    ) {
        return posOrderPort.getOrdersByTableId(
                buildGetOrdersByTableIdRequest(posOrganizationId, posTableId, orderStatus, dateFrom)
        );
    }

    private CreateOrderRequest buildCreateOrderRequest(String posTableId, String posTerminalGroupId,
                                                       String posOrganizationId) {
        CreateOrderRequest.Order order = CreateOrderRequest.Order
                .builder()
                .tableIds(Collections.singletonList(posTableId))
                .cart(cartService.getCartResponse())
                .build();

        return CreateOrderRequest
                .builder()
                .terminalGroupId(posTerminalGroupId)
                .organizationId(posOrganizationId)
                .order(order)
                .build();
    }

    private GetOrdersByTableIdRequest buildGetOrdersByTableIdRequest(
            String posOrganizationId,
            String posTableId,
            OrderStatus orderStatus,
            LocalDateTime dateFrom
    ) {
        return GetOrdersByTableIdRequest
                .builder()
                .organizationIds(List.of(posOrganizationId))
                .tableIds(List.of(posTableId))
                .statuses(List.of(orderStatus))
                .dateFrom(dateFrom)
                .build();
    }

    private GetOrderByIdRequest buildGetOrderByIdRequest(String posOrganizationId, String orderId) {
        return GetOrderByIdRequest
                .builder()
                .organizationIds(List.of(posOrganizationId))
                .orderIds(List.of(orderId))
                .build();
    }

    private AddOrderItemsRequest buildAddOrderItemsRequest(String posOrganizationId, String orderId) {
        return AddOrderItemsRequest
                .builder()
                .orderId(orderId)
                .organizationId(posOrganizationId)
                .cart(cartService.getCartResponse())
                .build();
    }

}
