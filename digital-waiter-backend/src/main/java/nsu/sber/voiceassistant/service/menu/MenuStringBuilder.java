package nsu.sber.voiceassistant.service.menu;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.MenuService;
import nsu.sber.voiceassistant.pipelinr.commands.menu.LlmMenuItem;
import nsu.sber.web.mapper.DishDtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
                sb.append("Блюдо: ").append(item.getName())
                        .append(" | itemId: ").append(item.getItemId());

                if (item.getItemSizes() != null && !item.getItemSizes().isEmpty()) {
                    sb.append(" | Размеры: ");
                    item.getItemSizes().forEach(size ->
                            sb.append(size.getSizeName())
                                    .append("(sizeId: ")
                                    .append(size.getSizeId())
                                    .append("), "));
                    sb.setLength(sb.length() - 2);
                } else {
                    sb.append(" | Размеры: default(sizeId: null)");
                }

                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public String buildCompactMenuForPrompt() {
        var menu = dishDtoMapper.menuResponseToDto(menuService.getMenu());
        StringBuilder sb = new StringBuilder();

        for (var category : menu.getItemCategories()) {
            if (category.getItems() == null || category.getItems().isEmpty()) {
                continue;
            }

            for (var item : category.getItems()) {
                sb.append(item.getName());

                if (item.getItemSizes() != null && !item.getItemSizes().isEmpty()) {
                    String sizes = item.getItemSizes().stream()
                            .map(size -> size.getSizeName() == null ? "default" : size.getSizeName())
                            .collect(Collectors.joining(", "));
                    sb.append(" | размеры: ").append(sizes);
                } else {
                    sb.append(" | размеры: default");
                }

                sb.append("\n");
            }
        }

        return sb.toString().trim();
    }

    public String formatRelevantMenu(List<LlmMenuItem> items) {
        if (items == null || items.isEmpty()) {
            return "Не нашёл подходящих блюд.";
        }

        StringBuilder sb = new StringBuilder();

        for (LlmMenuItem item : items) {
            sb.append(item.getName());

            if (item.getSizes() != null && !item.getSizes().isEmpty()) {
                sb.append(" — размеры: ")
                        .append(String.join(", ", item.getSizes()));
            }

            sb.append("\n");
        }

        return sb.toString().trim();
    }
}