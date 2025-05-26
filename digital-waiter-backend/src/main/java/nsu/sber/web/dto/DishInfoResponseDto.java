package nsu.sber.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class DishInfoResponseDto {
    private String itemId;
    private String name;
    private String description;
    private List<Allergen> allergens;
    private List<ItemSize> itemSizes;

    @Data
    @Builder
    public static class Allergen {
        private String name;
    }

    @Data
    @Builder
    public static class ItemSize {
        private String sizeName;
        private double portionWeightGrams;
        private String measureUnitType;
        private double price;
        private String buttonImageUrl;
        private NutritionPerHundredGrams nutritionPerHundredGrams;
    }

    @Data
    @Builder
    public static class NutritionPerHundredGrams {
        private double fats;
        private double proteins;
        private double carbs;
        private double energy;
    }
}
