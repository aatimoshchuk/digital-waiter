package nsu.sber.messaging.pos.iiko.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuResponseDto {
    private List<ItemCategory> itemCategories;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItemCategory {
        private String id;
        private String name;
        private List<Item> items;
        private boolean isHidden;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String itemId;
        private String name;
        private String description;
        private List<Allergen> allergens;
        private List<ItemSize> itemSizes;
        private boolean isHidden;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Allergen {
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NutritionPerHundredGrams {
        private double fats;
        private double proteins;
        private double carbs;
        private double energy;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Price {
        private String organizationId;
        private double price;
    }
}
