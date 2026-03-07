package nsu.sber.web.mapper;

import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.restaurant_table.CreateRestaurantTableRequest;
import nsu.sber.domain.model.restaurant_table.CreateRestaurantTableResponse;
import nsu.sber.domain.model.restaurant_table.RestaurantTableResponse;
import nsu.sber.domain.model.restaurant_table.UpdateRestaurantTableRequest;
import nsu.sber.web.dto.CreateRestaurantTableRequestDto;
import nsu.sber.web.dto.CreateRestaurantTableResponseDto;
import nsu.sber.web.dto.RestaurantTableDto;
import nsu.sber.web.dto.RestaurantTableResponseDto;
import nsu.sber.web.dto.UpdateRestaurantTableRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestaurantTableDtoMapper {

    CreateRestaurantTableRequest dtoToCreateRestaurantTableRequest(
            CreateRestaurantTableRequestDto createRestaurantTableRequestDto
    );

    RestaurantTableResponseDto restaurantTableResponseToDto(RestaurantTableResponse restaurantTableResponse);

    List<RestaurantTableDto> restaurantTablesToDtoList(List<RestaurantTable> restaurantTables);

    CreateRestaurantTableResponseDto createRestaurantTableResponseToDto(
            CreateRestaurantTableResponse createRestaurantTableResponse
    );

    UpdateRestaurantTableRequest dtoToUpdateRestaurantTableRequest(
            UpdateRestaurantTableRequestDto updateRestaurantTableRequestDto
    );

}
