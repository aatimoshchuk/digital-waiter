package nsu.sber.voiceassistant.application.nlp;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.MenuService;
import nsu.sber.web.mapper.DishDtoMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuStringBuilder {
    private final DishDtoMapper dishDtoMapper;
    private final MenuService menuService;

    public String buildMenuString() {
        var menu = dishDtoMapper.menuResponseToDto(menuService.getMenu());
        StringBuilder sb = new StringBuilder();

        for (var category : menu.getItemCategories()) {
            for (var item : category.getItems()) {
                sb.append("Блюдо: ").append(item.getName()).append(" | itemId: ").append(item.getItemId());

                if (item.getItemSizes() != null && !item.getItemSizes().isEmpty()) {
                    sb.append(" | Размеры: ");
                    item.getItemSizes().forEach(size -> sb.append(size.getSizeName()).append("(sizeId: ").append(size.getSizeId()).append("), "));
                    sb.setLength(sb.length() - 2);
                } else {
                    sb.append(" | Размеры: default(sizeId: null)");
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
