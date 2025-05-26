package nsu.sber.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuResponseDto {
    private List<ItemCategory> itemCategories;

    @Data
    @Builder
    public static class ItemCategory {
        private String id;
        private String name;
        private List<Item> items;
    }

    @Data
    @Builder
    public static class Item {
        private String itemId;
        private String name;
        private List<ItemSize> itemSizes;
    }

    @Data
    @Builder
    public static class ItemSize {
        private String sizeName;
        private double portionWeightGrams;
        private String measureUnitType;
        private double price;
        private String buttonImageUrl;
    }
}
