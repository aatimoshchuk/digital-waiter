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

    public RestaurantTable getRestaurantTableById(int id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.RestaurantTableNotFoundException(id));
    }
}
