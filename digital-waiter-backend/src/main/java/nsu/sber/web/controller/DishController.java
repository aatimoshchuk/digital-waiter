package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.DishService;
import nsu.sber.web.dto.DishInfoResponseDto;
import nsu.sber.web.dto.MenuResponseDto;
import nsu.sber.web.mapper.DishDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Меню", description = "API для взаимодействия с меню")
public class DishController {

    private final DishService dishService;
    private final DishDtoMapper dishDtoMapper;

    @GetMapping("/menu")
    @Operation(
            summary = "Получение меню",
            description = "Получение внешнего меню, подключенного к ApiLogin"
    )
    public MenuResponseDto getMenu() {
        return dishDtoMapper.menuResponseToDto(dishService.getMenu());
    }

    @GetMapping("/dish")
    @Operation(
            summary = "Получение информации о блюде",
            description = "Получение полной информации о блюде по его id"
    )
    public DishInfoResponseDto getDishInfo(@RequestParam(name = "id") String dishId) {
        return dishDtoMapper.dishInfoResponseToDto(dishService.getDishInfo(dishId));
    }
}
