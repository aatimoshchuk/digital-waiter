package nsu.sber.domain.model.menu;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuItem {
    private String itemId;
    private String name;
    private String description;
    private List<Allergen> allergens;
    private List<ItemSize> itemSizes;
    private boolean isHidden;

    @Data
    @Builder
    public static class Allergen {
        private String name;
    }

    @Data
    @Builder
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
    public static class NutritionPerHundredGrams {
        private double fats;
        private double proteins;
        private double carbs;
        private double energy;
    }

    @Data
    @Builder
    public static class Price {
        private String organizationId;
        private double price;
    }
}
