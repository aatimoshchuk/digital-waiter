package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.mapper.RestaurantTableEntityMapper;
import nsu.sber.db.repository.jpa.RestaurantTableRepository;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.port.repository.jpa.RestaurantTableRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<RestaurantTable> findByTerminalGroupId(Integer terminalGroupId) {
        return restaurantTableEntityMapper.entitiesToRestaurantTables(
                restaurantTableRepository.findByTerminalGroupId(terminalGroupId)
        );
    }

    @Override
    public boolean existsByTerminalGroupId(Integer terminalGroupId) {
        return restaurantTableRepository.existsByTerminalGroupId(terminalGroupId);
    }

}
