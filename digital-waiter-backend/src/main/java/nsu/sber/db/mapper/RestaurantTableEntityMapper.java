package nsu.sber.db.mapper;

import nsu.sber.db.entity.RestaurantTableEntity;
import nsu.sber.domain.model.entity.RestaurantTable;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestaurantTableEntityMapper {

    RestaurantTable entityToRestaurantTable(RestaurantTableEntity restaurantTableEntity);

    RestaurantTableEntity restaurantTableToEntity(RestaurantTable restaurantTable);

    List<RestaurantTable> entitiesToRestaurantTables(List<RestaurantTableEntity> entityList);

}
