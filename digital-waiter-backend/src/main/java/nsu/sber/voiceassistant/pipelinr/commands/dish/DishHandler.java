package nsu.sber.voiceassistant.pipelinr.commands.dish;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;

import nsu.sber.domain.service.MenuService;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.web.dto.MenuItemResponseDto;
import nsu.sber.web.mapper.DishDtoMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishHandler implements Command.Handler<DishCommand, ProcessingResponse> {

    private final MenuService menuService;
    private final DishDtoMapper dishDtoMapper;

    private String toReadableString(MenuItemResponseDto dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("Блюдо:\n")
                .append("• ID: ").append(dto.getItemId()).append("\n")
                .append("• Название: ").append(dto.getName()).append("\n")
                .append("• Описание: ").append(dto.getDescription()).append("\n\n");

        sb.append("Аллергены:\n");
        if (dto.getAllergens() == null || dto.getAllergens().isEmpty()) {
            sb.append("  • отсутствуют\n\n");
        } else {
            dto.getAllergens().forEach(a -> sb.append("  • ").append(a.getName()).append("\n"));
            sb.append("\n");
        }

        sb.append("Размеры блюда:\n");
        if (dto.getItemSizes() == null || dto.getItemSizes().isEmpty()) {
            sb.append("  • отсутствуют\n");
        } else {
            dto.getItemSizes().forEach(size -> {
                sb.append("  ● Размер: ").append(size.getSizeName()).append("\n")
                        .append("    - sizeId: ").append(size.getSizeId()).append("\n")
                        .append("    - Вес: ").append(size.getPortionWeightGrams()).append(" г\n")
                        .append("    - Ед. изм.: ").append(size.getMeasureUnitType()).append("\n")
                        .append("    - Цена: ").append(size.getPrice()).append("\n")
                        .append("    - Картинка: ").append(size.getButtonImageUrl()).append("\n");

                if (size.getNutritionPerHundredGrams() != null) {
                    sb.append("    Пищевая ценность (на 100 г):\n")
                            .append("      • БЖУ: ")
                            .append(size.getNutritionPerHundredGrams().getProteins()).append("/")
                            .append(size.getNutritionPerHundredGrams().getFats()).append("/")
                            .append(size.getNutritionPerHundredGrams().getCarbs()).append("\n")
                            .append("      • Энергоценность: ")
                            .append(size.getNutritionPerHundredGrams().getEnergy())
                            .append(" ккал\n");
                }

                sb.append("\n");
            });
        }

        return sb.toString().trim();
    }

    @Override
    public ProcessingResponse handle(DishCommand command) {
        StringBuilder result = new StringBuilder();

        for (var dish : command.getItems()) {
            var info = menuService.getDishInfo(dish.getItemId());
            result.append(toReadableString(dishDtoMapper.menuItemToDishInfoResponseDto(info))).append("\n\n");
        }

        return ProcessingResponse.builder()
                .success(true)
                .transcribedText(result.toString().trim())
                .build();
    }
}
