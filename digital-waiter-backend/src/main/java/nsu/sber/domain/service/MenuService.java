package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.menu.MenuItem;
import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.domain.model.menu.MenuResponse;
import nsu.sber.domain.model.menu.MenuResponse.ItemCategory;
import nsu.sber.domain.port.pos.PosMenuPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final PosMenuPort posMenuPort;
    private final RequestContextProvider requestContextProvider;

    public MenuResponse getMenu() {
        MenuResponse menuResponse = posMenuPort.getMenu(buildMenuRequest())
                .orElseThrow(() -> new DigitalWaiterException.ExternalMenuNotFoundException(
                        requestContextProvider.getExternalMenuId()
                ));

        return new MenuResponse(
                filterCategoriesByVisibility(menuResponse.getItemCategories(),
                requestContextProvider.getOrganizationId())
        );
    }

    public MenuItem getDishInfo(String dishId) {
       return findItemById(getMenu(), dishId)
                .orElseThrow(() -> new DigitalWaiterException.DishNotFoundException(dishId));
    }

    public Optional<MenuItem> findItemById(MenuResponse menuResponse, String id) {
        return menuResponse.getItemCategories().stream()
                .filter(Objects::nonNull)
                .flatMap(c -> c.getItems().stream())
                .filter(i -> id.equals(i.getItemId()))
                .findFirst();
    }

    private MenuRequest buildMenuRequest() {
        return MenuRequest
                .builder()
                .organizationIds(Collections.singletonList(requestContextProvider.getOrganizationId()))
                .externalMenuId(requestContextProvider.getExternalMenuId())
                .build();
    }

    private List<ItemCategory> filterCategoriesByVisibility(List<ItemCategory> categories, String organizationId) {
        return categories.stream()
                .filter(c -> !c.isHidden())
                .map(c -> MenuResponse.ItemCategory.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .items(filterItemsByVisibility(c.getItems(), organizationId))
                        .build()
                )
                .filter(c -> !c.getItems().isEmpty())
                .toList();
    }

    private List<MenuItem> filterItemsByVisibility(List<MenuItem> items, String organizationId) {
        return items.stream()
                .filter(i -> !i.isHidden())
                .map(i -> MenuItem.builder()
                        .itemId(i.getItemId())
                        .name(i.getName())
                        .description(i.getDescription())
                        .allergens(i.getAllergens())
                        .itemSizes(filterSizesByOrganizationIdAndVisibility(i.getItemSizes(), organizationId))
                        .build()
                )
                .filter(i -> !i.getItemSizes().isEmpty())
                .toList();
    }

    private List<MenuItem.ItemSize> filterSizesByOrganizationIdAndVisibility(
            List<MenuItem.ItemSize> sizes,
            String organizationId
    ) {
        return sizes.stream()
                .filter(s -> !s.isHidden())
                .map(s -> MenuItem.ItemSize.builder()
                        .sizeId(s.getSizeId())
                        .sizeName(s.getSizeName())
                        .portionWeightGrams(s.getPortionWeightGrams())
                        .measureUnitType(s.getMeasureUnitType())
                        .prices(s.getPrices().stream()
                                .filter(p -> organizationId.equals(p.getOrganizationId()))
                                .toList())
                        .buttonImageUrl(s.getButtonImageUrl())
                        .nutritionPerHundredGrams(s.getNutritionPerHundredGrams())
                        .build()
                )
                .filter(s -> !s.getPrices().isEmpty())
                .toList();
    }
}
