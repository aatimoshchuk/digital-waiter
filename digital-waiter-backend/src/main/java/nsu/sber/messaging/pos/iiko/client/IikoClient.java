package nsu.sber.messaging.pos.iiko.client;

import nsu.sber.messaging.pos.iiko.config.IikoFeignConfig;
import nsu.sber.messaging.pos.iiko.dto.AddOrderItemsRequestDto;
import nsu.sber.messaging.pos.iiko.dto.AddOrderItemsResponseDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderRequestDto;
import nsu.sber.messaging.pos.iiko.dto.CreateOrderResponseDto;
import nsu.sber.messaging.pos.iiko.dto.MenuRequestDto;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import nsu.sber.messaging.pos.iiko.dto.OperationStatusRequestDto;
import nsu.sber.messaging.pos.iiko.dto.OperationStatusResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "iiko-client",
        contextId = "iikoClient",
        url = "${iiko.url}",
        configuration = IikoFeignConfig.class
)
public interface IikoClient {

    @PostMapping("/api/2/menu/by_id")
    MenuResponseDto getMenu(
            @RequestHeader("Authorization") String token,
            @RequestBody MenuRequestDto menuRequestDto
    );

    @PostMapping("/api/1/order/create")
    CreateOrderResponseDto createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateOrderRequestDto createOrderRequestDto
    );

    @PostMapping("/api/1/commands/status")
    OperationStatusResponseDto getOperationStatus(
            @RequestHeader("Authorization") String token,
            @RequestBody OperationStatusRequestDto operationStatusRequestDto
    );

    @PostMapping("/api/1/order/add_items")
    AddOrderItemsResponseDto addOrderItems(
            @RequestHeader("Authorization") String token,
            @RequestBody AddOrderItemsRequestDto addOrderItemsRequestDto
    );

}
