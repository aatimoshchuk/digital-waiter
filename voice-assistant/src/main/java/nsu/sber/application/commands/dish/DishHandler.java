package nsu.sber.application.commands.dish;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.MenuService;
import nsu.sber.web.dto.MenuItemResponseDto;
import nsu.sber.web.mapper.DishDtoMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishHandler implements Command.Handler<DishCommand, MenuItemResponseDto> {

    private final MenuService menuService;
    private final DishDtoMapper dishDtoMapper;

    @Override
    public MenuItemResponseDto handle(DishCommand command) {
        var dish = menuService.getDishInfo(command.getDishId());
        return dishDtoMapper.menuItemToDishInfoResponseDto(dish);
    }
}
