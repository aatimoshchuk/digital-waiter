package nsu.sber.domain.port.pos;

import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.domain.model.menu.StopList;
import nsu.sber.domain.model.menu.StopListRequest;

import java.util.Optional;

public interface PosMenuPort {

    Optional<Menu> getMenu(MenuRequest menuRequest);

    Optional<Menu> getMenu(MenuRequest menuRequest, Integer organizationId, String apiKeyEncrypted);

    StopList getStopList(StopListRequest stopListRequest);

    StopList getStopList(StopListRequest stopListRequest, Integer organizationId, String apiKeyEncrypted);

}
