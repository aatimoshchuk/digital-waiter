package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.RestaurantTable;
import nsu.sber.domain.port.RestaurantTableRepositoryPort;
import nsu.sber.exception.ErrorType;
import nsu.sber.exception.ServiceException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantTableService {

    private final RestaurantTableRepositoryPort restaurantTableRepository;

    public RestaurantTable getRestaurantTableById(int id) {
        RestaurantTable restaurantTable = restaurantTableRepository.findById(id);

        if (restaurantTable == null) {
            String errorMessage = "Стол с id %d не найден".formatted(id);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.RESTAURANT_TABLE_NOT_FOUND);
        }

        return restaurantTable;
    }
}
