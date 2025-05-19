package nsu.sber.domain.port;

import nsu.sber.domain.model.RestaurantTable;

public interface RestaurantTableRepositoryPort {

    RestaurantTable findById(int id);

}
