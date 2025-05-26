package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.DishInfoResponse;
import nsu.sber.domain.model.MenuResponse;
import nsu.sber.domain.port.PosDishPort;
import nsu.sber.exception.ErrorType;
import nsu.sber.exception.ServiceException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishService {

    private final PosDishPort posDishPort;
    private final RequestContextProvider requestContextProvider;

    public MenuResponse getMenu() {
        MenuResponse menuResponse = posDishPort.getMenu();

        if (menuResponse == null) {

            String errorMessage = "Меню с id %s не найдено".formatted(requestContextProvider.getExternalMenuId());
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.EXTERNAL_MENU_NOT_FOUND);
        }

        return menuResponse;
    }

    public DishInfoResponse getDishInfo(String dishId) {
        DishInfoResponse dishInfoResponse = posDishPort.getDishInfo(dishId);

        if (dishInfoResponse == null) {
            String errorMessage = "Блюдо с id %s не найдено".formatted(dishId);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.DISH_NOT_FOUND);
        }

        return dishInfoResponse;
    }
}
