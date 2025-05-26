package nsu.sber.domain.port;

import nsu.sber.domain.model.DishInfoResponse;
import nsu.sber.domain.model.MenuResponse;

public interface PosDishPort {

    MenuResponse getMenu();

    DishInfoResponse getDishInfo(String dishId);

}
