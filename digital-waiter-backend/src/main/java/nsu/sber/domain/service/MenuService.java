package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.Menu.ItemCategory;
import nsu.sber.domain.model.menu.MenuItem;
import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.domain.port.pos.PosMenuPort;
import nsu.sber.domain.port.repository.redis.MenuRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final TerminalGroupService terminalGroupService;
    private final OrganizationService organizationService;

    private final PosMenuPort posMenuPort;
    private final MenuRepositoryPort menuRepositoryPort;

    public Menu getMenu() {
        TerminalGroup terminalGroup = terminalGroupService.getCurrentTerminalGroup();

        return menuRepositoryPort.findByTerminalGroupId(terminalGroup.getId())
                .orElse(loadMenu(terminalGroup));
    }

    public MenuItem getDishInfo(String dishId) {
        return findItemById(getMenu(), dishId)
                .orElseThrow(() -> new DigitalWaiterException.DishNotFoundException(dishId));
    }

    public Optional<MenuItem> findItemById(Menu menu, String id) {
        return menu.getItemCategories().stream()
                .filter(Objects::nonNull)
                .flatMap(c -> c.getItems().stream())
                .filter(i -> id.equals(i.getItemId()))
                .findFirst();
    }

    public boolean existsItemById(String id) {
        return getMenu().getItemCategories().stream()
                .filter(Objects::nonNull)
                .flatMap(c -> c.getItems().stream())
                .filter(Objects::nonNull)
                .anyMatch(i -> id.equals(i.getItemId()));
    }

    public boolean existsItemSizeById(String itemId, String sizeId) {
        return getMenu().getItemCategories().stream()
                .filter(Objects::nonNull)
                .flatMap(c -> c.getItems().stream())
                .filter(Objects::nonNull)
                .filter(i -> itemId.equals(i.getItemId()))
                .flatMap(i -> {
                    List<MenuItem.ItemSize> sizes = i.getItemSizes();
                    return sizes == null ? Stream.empty() : sizes.stream();
                })
                .anyMatch(s -> sizeId.equals(s.getSizeId()));
    }

    private Menu loadMenu(TerminalGroup terminalGroup) {
        String posOrganizationId = organizationService
                .getOrganization(terminalGroup.getOrganizationId())
                .getPosOrganizationId();
        String posExternalMenuId = terminalGroup.getPosExternalMenuId();

        Menu menu = posMenuPort.getMenu(buildMenuRequest(posOrganizationId, posExternalMenuId))
                .orElseThrow(() -> new DigitalWaiterException.ExternalMenuNotFoundException(
                        posExternalMenuId
                ));

        Menu filteredMenu =  new Menu(
                filterCategoriesByVisibility(menu.getItemCategories(), posOrganizationId)
        );

        menuRepositoryPort.save(terminalGroup.getId(), filteredMenu);

        return filteredMenu;
    }

    private MenuRequest buildMenuRequest(String organizationId, String externalMenuId) {
        return MenuRequest
                .builder()
                .organizationIds(Collections.singletonList(organizationId))
                .externalMenuId(externalMenuId)
                .build();
    }

    private List<ItemCategory> filterCategoriesByVisibility(List<ItemCategory> categories, String organizationId) {
        return categories.stream()
                .filter(c -> !c.isHidden())
                .map(c -> Menu.ItemCategory.builder()
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
