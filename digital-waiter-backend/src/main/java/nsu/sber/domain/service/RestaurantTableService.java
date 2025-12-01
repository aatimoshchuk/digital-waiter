package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.port.repository.jpa.RestaurantTableRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {
    private final RestaurantTableRepositoryPort restaurantTableRepository;
    private final UserService userService;

    public RestaurantTable getRestaurantTableById(int id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.RestaurantTableNotFoundException(id));
    }

    public RestaurantTable getCurrentRestaurantTable() {
        RestaurantTable restaurantTable = userService.getCurrentUser().getRestaurantTable();

        if (restaurantTable == null) {
            throw new DigitalWaiterException.UserHasNoRestaurantTableException();
        }
        return restaurantTable;
    }
}
