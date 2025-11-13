package nsu.sber.domain.port.pos;

import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.domain.model.menu.MenuResponse;

import java.util.Optional;

public interface PosMenuPort {

    Optional<MenuResponse> getMenu(MenuRequest menuRequest);

}
