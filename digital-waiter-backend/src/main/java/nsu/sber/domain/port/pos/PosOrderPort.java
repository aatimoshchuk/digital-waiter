package nsu.sber.domain.port.pos;

import nsu.sber.domain.model.order.AddOrderItemsRequest;
import nsu.sber.domain.model.order.AddOrderItemsResponse;
import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.domain.model.order.GetOrderByIdRequest;
import nsu.sber.domain.model.order.GetOrdersByTableIdRequest;
import nsu.sber.domain.model.order.GetOrdersResponse;

public interface PosOrderPort {

    CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest);

    AddOrderItemsResponse addOrderItems(AddOrderItemsRequest addOrderItemsRequest);

    GetOrdersResponse getOrdersByTableId(GetOrdersByTableIdRequest getOrdersByTableIdRequest);

    GetOrdersResponse getOrderById(GetOrderByIdRequest getOrderByIdRequest);

}
