package nsu.sber.web.mapper;

import nsu.sber.domain.model.order.AddOrderItemsResponse;
import nsu.sber.domain.model.order.CreateOrderResponse;
import nsu.sber.web.dto.AddOrderItemsResponseDto;
import nsu.sber.web.dto.CreateOrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderDtoMapper {

    CreateOrderResponseDto createOrderResponseToDto(CreateOrderResponse createOrderResponse);

    AddOrderItemsResponseDto addOrderItemsResponseToDto(AddOrderItemsResponse addOrderItemsResponse);
}
