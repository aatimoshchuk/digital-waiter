package nsu.sber.domain.model.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private String itemId;
    private String name;
    private String description;
    private List<Allergen> allergens;
    private List<ItemSize> itemSizes;
    private boolean isHidden;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Allergen {
        private String name;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemSize {
        private String sizeId;
        private String sizeName;
        private double portionWeightGrams;
        private List<Price> prices;
        private String buttonImageUrl;
        private NutritionPerHundredGrams nutritionPerHundredGrams;
        private boolean isHidden;
        private String measureUnitType;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NutritionPerHundredGrams {
        private double fats;
        private double proteins;
        private double carbs;
        private double energy;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Price {
        private String organizationId;
        private double price;
    }
}
