package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.RestaurantTable;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepositoryPort {

    Optional<RestaurantTable> findById(int id);

    List<RestaurantTable> findAll();

    List<RestaurantTable> findByTerminalGroupId(Integer terminalGroupId);

    boolean existsByTerminalGroupId(Integer terminalGroupId);

    boolean existsByPosTableIdAndTerminalGroupId(String posTableId, Integer terminalGroupId);

    RestaurantTable save(RestaurantTable restaurantTable);

    void delete(RestaurantTable restaurantTable);

    void deleteByTerminalGroupId(Integer terminalGroupId);

}
