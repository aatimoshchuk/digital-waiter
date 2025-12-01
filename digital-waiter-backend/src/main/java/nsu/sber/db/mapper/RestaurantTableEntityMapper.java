package nsu.sber.db.mapper;

import nsu.sber.db.entity.RestaurantTableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestaurantTableEntityMapper {

    nsu.sber.domain.model.entity.RestaurantTable entityToRestaurantTable(RestaurantTableEntity restaurantTableEntity);

    RestaurantTableEntity restaurantTableToEntity(nsu.sber.domain.model.entity.RestaurantTable restaurantTable);

}
