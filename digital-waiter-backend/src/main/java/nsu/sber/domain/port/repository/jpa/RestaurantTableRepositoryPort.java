package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.RestaurantTable;

import java.util.Optional;

public interface RestaurantTableRepositoryPort {

    Optional<RestaurantTable> findById(int id);

}
