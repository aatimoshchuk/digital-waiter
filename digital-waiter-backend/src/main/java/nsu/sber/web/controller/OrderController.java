package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.OrderService;
import nsu.sber.web.dto.CreateOrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "Order Controller")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(
            summary = "Create an order",
            description = "Submits the order to the iiko system"
    )
    public CreateOrderResponse createOrder() {
        return new CreateOrderResponse(orderService.createOrderAsync());
    }

}
