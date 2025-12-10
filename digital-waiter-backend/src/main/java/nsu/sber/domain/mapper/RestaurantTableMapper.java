package nsu.sber.domain.mapper;

import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.restaurant_table.CreateRestaurantTableRequest;
import nsu.sber.domain.model.restaurant_table.RestaurantTableResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestaurantTableMapper {

    @Mapping(target = "id", source = "restaurantTable.id")
    @Mapping(target = "name", source = "restaurantTable.name")
    @Mapping(target = "posTableId", source = "restaurantTable.posTableId")
    @Mapping(target = "terminalGroupId", source = "restaurantTable.terminalGroupId")
    RestaurantTableResponse restaurantTableToResponse(RestaurantTable restaurantTable, String login);

    @Mapping(target = "id", ignore = true)
    RestaurantTable createRestaurantTableRequestToRestaurantTable(CreateRestaurantTableRequest request);

}
