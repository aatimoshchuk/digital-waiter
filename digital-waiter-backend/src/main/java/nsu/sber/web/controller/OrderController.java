package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.OrderService;
import nsu.sber.web.dto.AddOrderItemsResponseDto;
import nsu.sber.web.dto.CreateOrderResponseDto;
import nsu.sber.web.mapper.OrderDtoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "Order Controller")
public class OrderController {
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping("/create")
    @Operation(
            summary = "Create an order",
            description = "Submits the order to the iiko system"
    )
    public CreateOrderResponseDto createOrder() {
        return orderDtoMapper.createOrderResponseToDto(orderService.createOrderAsync());
    }

    @PostMapping("/add-items")
    @Operation(
            summary = "Add items from cart to an existing order",
            description = """
                    Submits the current cart contents as additional items to an already created order
                    associated with the current table in the iiko system
                    """
    )
    public AddOrderItemsResponseDto addOrderItems(@RequestParam(name = "id") String orderId) {
        return orderDtoMapper.addOrderItemsResponseToDto(orderService.addOrderItemsAsync(orderId));
    }

}
