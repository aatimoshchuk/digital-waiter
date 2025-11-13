package nsu.sber.domain.port.pos;

import nsu.sber.domain.model.order.CreateOrderRequest;
import nsu.sber.domain.model.order.CreateOrderResponse;

public interface PosOrderPort {

    CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest);

}
