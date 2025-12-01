package nsu.sber.messaging.pos.iiko.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.domain.port.pos.PosMenuPort;
import nsu.sber.messaging.pos.iiko.client.IikoClient;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import nsu.sber.messaging.pos.iiko.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PosMenuAdapter implements PosMenuPort {

    private final IikoClient iikoClient;
    private final MenuMapper menuMapper;

    @Override
    public Optional<Menu> getMenu(MenuRequest menuRequest) {
        MenuResponseDto menuResponseDto = iikoClient.getMenu(
                null,
                menuMapper.menuRequestToDto(menuRequest)
        );

        return Optional.ofNullable(menuMapper.menuResponseDtoToMenu(menuResponseDto));
    }

}
