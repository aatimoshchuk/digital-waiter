package nsu.sber.db.mapper;

import javax.annotation.processing.Generated;
import nsu.sber.db.entity.RestaurantTableEntity;
import nsu.sber.domain.model.entity.RestaurantTable;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-03T12:15:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class RestaurantTableEntityMapperImpl implements RestaurantTableEntityMapper {

    @Override
    public RestaurantTable entityToRestaurantTable(RestaurantTableEntity restaurantTable) {
        if ( restaurantTable == null ) {
            return null;
        }

        RestaurantTable.RestaurantTableBuilder restaurantTable1 = RestaurantTable.builder();

        restaurantTable1.id( restaurantTable.getId() );
        restaurantTable1.posTableId( restaurantTable.getPosTableId() );
        restaurantTable1.terminalGroupId( restaurantTable.getTerminalGroupId() );

        return restaurantTable1.build();
    }

    @Override
    public RestaurantTableEntity restaurantTableToEntity(RestaurantTable restaurantTable) {
        if ( restaurantTable == null ) {
            return null;
        }

        RestaurantTableEntity restaurantTableEntity = new RestaurantTableEntity();

        restaurantTableEntity.setId( restaurantTable.getId() );
        restaurantTableEntity.setPosTableId( restaurantTable.getPosTableId() );
        restaurantTableEntity.setTerminalGroupId( restaurantTable.getTerminalGroupId() );

        return restaurantTableEntity;
    }
}
