package nsu.sber.messaging.pos.iiko.mapper;

import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.domain.model.menu.MenuResponse;
import nsu.sber.messaging.pos.iiko.dto.MenuRequestDto;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuResponse dtoToMenuResponse(MenuResponseDto menuResponseDto);

    MenuRequestDto menuRequestToDto(MenuRequest menuRequest);

}
