package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.mapper.RestaurantTableEntityMapper;
import nsu.sber.db.repository.jpa.RestaurantTableRepository;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.port.repository.jpa.RestaurantTableRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantTableRepositoryService implements RestaurantTableRepositoryPort {

    private final RestaurantTableRepository restaurantTableRepository;
    private final RestaurantTableEntityMapper restaurantTableEntityMapper;

    @Override
    public Optional<RestaurantTable> findById(int id) {
        return restaurantTableRepository.findById(id)
                .map(restaurantTableEntityMapper::entityToRestaurantTable);
    }
}
