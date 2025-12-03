package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.port.repository.jpa.RestaurantTableRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {
    private final RestaurantTableRepositoryPort restaurantTableRepository;
    private final UserService userService;

    public RestaurantTable getRestaurantTableById(int id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.RestaurantTableWithThisIdNotFoundException(id));
    }

    public RestaurantTable getCurrentRestaurantTable() {
        return getRestaurantTableById(userService.getCurrentUser().getRestaurantTableId());
    }

    public List<RestaurantTable> findByTerminalGroupId(Integer terminalGroupId) {
        return restaurantTableRepository.findByTerminalGroupId(terminalGroupId);
    }

    public boolean existsByTerminalGroupId(Integer terminalGroupId) {
        return restaurantTableRepository.existsByTerminalGroupId(terminalGroupId);
    }
}
