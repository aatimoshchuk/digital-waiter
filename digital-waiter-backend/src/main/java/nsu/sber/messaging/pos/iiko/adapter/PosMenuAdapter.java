package nsu.sber.messaging.pos.iiko.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.domain.model.menu.StopList;
import nsu.sber.domain.model.menu.StopListRequest;
import nsu.sber.domain.port.pos.PosMenuPort;
import nsu.sber.messaging.pos.iiko.client.IikoClient;
import nsu.sber.messaging.pos.iiko.config.IikoAuthTokenProvider;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import nsu.sber.messaging.pos.iiko.dto.StopListResponseDto;
import nsu.sber.messaging.pos.iiko.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PosMenuAdapter implements PosMenuPort {

    private final IikoClient iikoClient;
    private final MenuMapper menuMapper;
    private final IikoAuthTokenProvider tokenProvider;

    @Override
    public Optional<Menu> getMenu(MenuRequest menuRequest) {
        MenuResponseDto menuResponseDto = iikoClient.getMenu(
                null,
                menuMapper.menuRequestToDto(menuRequest)
        );

        return Optional.ofNullable(menuMapper.menuResponseDtoToMenu(menuResponseDto));
    }

    @Override
    public Optional<Menu> getMenu(MenuRequest menuRequest, Integer organizationId, String apiKeyEncrypted) {
        String token = tokenProvider.getTokenByOrganizationIdAndApiKey(
                organizationId,
                apiKeyEncrypted
        );

        MenuResponseDto menuResponseDto = iikoClient.getMenu(
                "Bearer " + token,
                menuMapper.menuRequestToDto(menuRequest)
        );

        return Optional.ofNullable(menuMapper.menuResponseDtoToMenu(menuResponseDto));
    }

    @Override
    public StopList getStopList(StopListRequest stopListRequest) {
        StopListResponseDto stopListResponseDto = iikoClient.getStopList(
                null,
                menuMapper.stopListRequestToDto(stopListRequest)
        );

        return menuMapper.stopListResponseDtoToStopList(stopListResponseDto);
    }

    @Override
    public StopList getStopList(StopListRequest stopListRequest, Integer organizationId, String apiKeyEncrypted) {
        String token = tokenProvider.getTokenByOrganizationIdAndApiKey(
                organizationId,
                apiKeyEncrypted
        );

        StopListResponseDto stopListResponseDto = iikoClient.getStopList(
                "Bearer " + token,
                menuMapper.stopListRequestToDto(stopListRequest)
        );

        return menuMapper.stopListResponseDtoToStopList(stopListResponseDto);
    }

}
