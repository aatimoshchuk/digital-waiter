package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.MenuService;
import nsu.sber.web.dto.MenuItemResponseDto;
import nsu.sber.web.dto.MenuResponseDto;
import nsu.sber.web.mapper.DishDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
@Tag(name = "Menu Controller")
public class MenuController {

    private final MenuService menuService;
    private final DishDtoMapper dishDtoMapper;

    @GetMapping
    @Operation(
            summary = "Get the menu",
            description = "Retrieves the external menu associated with the ApiLogin"
    )
    public MenuResponseDto getMenu() {
        return dishDtoMapper.menuResponseToDto(menuService.getMenu());
    }

    @GetMapping("/dish")
    @Operation(
            summary = "Get dish details",
            description = "Retrieves complete information about a dish by its ID"
    )
    public MenuItemResponseDto getDishInfo(@RequestParam(name = "id") String dishId) {
        return dishDtoMapper.menuItemToDishInfoResponseDto(menuService.getDishInfo(dishId));
    }
}
