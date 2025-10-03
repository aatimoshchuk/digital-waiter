package nsu.sber.messaging.pos.iiko.mapper;

import lombok.RequiredArgsConstructor;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.DishInfoResponse;
import nsu.sber.domain.model.MenuResponse;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MenuResponseDtoMapper {

    private final RequestContextProvider requestContextProvider;

    public MenuResponse dtoToMenuResponse(MenuResponseDto menuResponseDto) {
        String orgId = requestContextProvider.getOrganizationId();

        List<MenuResponse.ItemCategory> categories = menuResponseDto.getItemCategories().stream()
                .filter(category -> !category.isHidden())
                .map(category -> {
                    List<MenuResponse.Item> items = category.getItems().stream()
                            .filter(item -> !item.isHidden())
                            .map(item -> {
                                List<MenuResponse.ItemSize> sizes = item.getItemSizes().stream()
                                        .filter(size -> !size.isHidden())
                                        .map(size -> size.getPrices().stream()
                                                .filter(price -> orgId.equals(price.getOrganizationId()))
                                                .findFirst()
                                                .map(price -> MenuResponse.ItemSize
                                                        .builder()
                                                        .sizeId(size.getSizeId())
                                                        .sizeName(size.getSizeName())
                                                        .measureUnitType(size.getMeasureUnitType())
                                                        .price(price.getPrice())
                                                        .buttonImageUrl(size.getButtonImageUrl())
                                                        .portionWeightGrams(size.getPortionWeightGrams())
                                                        .build())
                                                .orElse(null)
                                        )
                                        .filter(Objects::nonNull)
                                        .toList();

                                return MenuResponse.Item
                                        .builder()
                                        .itemId(item.getItemId())
                                        .name(item.getName())
                                        .itemSizes(sizes)
                                        .build();
                            })
                            .filter(Objects::nonNull)
                            .toList();

                    return MenuResponse.ItemCategory
                            .builder()
                            .id(category.getId())
                            .name(category.getName())
                            .items(items)
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        return MenuResponse
                .builder()
                .itemCategories(categories)
                .build();
    }

    public DishInfoResponse dtoToDishInfoResponseById(MenuResponseDto menuResponseDto, String id) {
        String orgId = requestContextProvider.getOrganizationId();

        return menuResponseDto.getItemCategories().stream()
                .flatMap(category -> category.getItems().stream())
                .filter(item -> id.equals(item.getItemId()))
                .findFirst()
                .filter(item -> !item.isHidden())
                .map(item -> {
                    List<DishInfoResponse.ItemSize> sizes = item.getItemSizes().stream()
                            .filter(itemSize -> !itemSize.isHidden())
                            .map(itemSize -> itemSize.getPrices().stream()
                                    .filter(price -> orgId.equals(price.getOrganizationId()))
                                    .findFirst()
                                    .map(price -> DishInfoResponse.ItemSize
                                            .builder()
                                            .sizeId(itemSize.getSizeId())
                                            .sizeName(itemSize.getSizeName())
                                            .portionWeightGrams(itemSize.getPortionWeightGrams())
                                            .measureUnitType(itemSize.getMeasureUnitType())
                                            .price(price.getPrice())
                                            .buttonImageUrl(itemSize.getButtonImageUrl())
                                            .nutritionPerHundredGrams(DishInfoResponse.NutritionPerHundredGrams
                                                    .builder()
                                                    .carbs(itemSize.getNutritionPerHundredGrams().getCarbs())
                                                    .energy(itemSize.getNutritionPerHundredGrams().getEnergy())
                                                    .fats(itemSize.getNutritionPerHundredGrams().getFats())
                                                    .proteins(itemSize.getNutritionPerHundredGrams().getProteins())
                                                    .build())
                                            .build())
                                    .orElse(null)
                            )
                            .filter(Objects::nonNull)
                            .toList();

                    List<DishInfoResponse.Allergen> allergens = item.getAllergens().stream()
                            .map(allergen -> DishInfoResponse.Allergen
                                    .builder()
                                    .name(allergen.getName())
                                    .build())
                            .filter(Objects::nonNull)
                            .toList();

                    return DishInfoResponse
                            .builder()
                            .itemId(item.getItemId())
                            .name(item.getName())
                            .description(item.getDescription())
                            .itemSizes(sizes)
                            .allergens(allergens)
                            .build();
                })
                .orElse(null);
    }
}
