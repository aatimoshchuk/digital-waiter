package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.RestaurantTableEntity;
import nsu.sber.db.mapper.RestaurantTableEntityMapper;
import nsu.sber.db.repository.RestaurantTableRepository;
import nsu.sber.domain.model.RestaurantTable;
import nsu.sber.domain.port.RestaurantTableRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantTableRepositoryService implements RestaurantTableRepositoryPort {

    private final RestaurantTableRepository restaurantTableRepository;
    private final RestaurantTableEntityMapper restaurantTableEntityMapper;

    @Override
    public RestaurantTable findById(int id) {
        RestaurantTableEntity restaurantTableEntity = restaurantTableRepository.findById(id);
        return restaurantTableEntityMapper.entityToRestaurantTable(restaurantTableEntity);
    }
}
