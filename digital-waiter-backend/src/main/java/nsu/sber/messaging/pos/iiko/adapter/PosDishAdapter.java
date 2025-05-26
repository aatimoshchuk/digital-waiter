package nsu.sber.messaging.pos.iiko.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.DishInfoResponse;
import nsu.sber.domain.model.MenuResponse;
import nsu.sber.domain.port.PosDishPort;
import nsu.sber.messaging.pos.iiko.client.IikoClient;
import nsu.sber.messaging.pos.iiko.dto.MenuRequestDto;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import nsu.sber.messaging.pos.iiko.mapper.MenuResponseDtoMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PosDishAdapter implements PosDishPort {

    private final IikoClient iikoClient;
    private final MenuResponseDtoMapper menuResponseDtoMapper;
    private final RequestContextProvider requestContextProvider;

    @Override
    public MenuResponse getMenu() {
        return menuResponseDtoMapper.dtoToMenuResponse(getMenuResponseDto());
    }

    @Override
    public DishInfoResponse getDishInfo(String dishId) {
        return menuResponseDtoMapper.dtoToDishInfoResponseById(getMenuResponseDto(), dishId);
    }

    private MenuResponseDto getMenuResponseDto() {
        List<String> organizationIds = new ArrayList<>();
        organizationIds.add(requestContextProvider.getOrganizationId());

        MenuRequestDto menuRequestDto = MenuRequestDto
                .builder()
                .organizationIds(organizationIds)
                .externalMenuId(requestContextProvider.getExternalMenuId())
                .build();

        return iikoClient.getMenu(null, menuRequestDto);
    }
}
