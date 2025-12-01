package nsu.sber.messaging.pos.iiko.mapper;

import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.messaging.pos.iiko.dto.MenuRequestDto;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    Menu menuResponseDtoToMenu(MenuResponseDto menuResponseDto);

    MenuRequestDto menuRequestToDto(MenuRequest menuRequest);

}
