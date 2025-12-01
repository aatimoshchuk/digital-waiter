package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import nsu.sber.util.ApiConstants;

import java.util.List;

@Data
@Builder
public class MenuResponseDto {

    @Schema(description = ApiConstants.MENU_ITEM_CATEGORIES_DESCRIPTION)
    private List<ItemCategory> itemCategories;

    @Data
    @Builder
    public static class ItemCategory {

        @Schema(description = ApiConstants.CATEGORY_ID_DESCRIPTION, example = ApiConstants.CATEGORY_ID_EXAMPLE)
        private String id;

        @Schema(description = ApiConstants.CATEGORY_NAME_DESCRIPTION, example = ApiConstants.CATEGORY_NAME_EXAMPLE)
        private String name;

        @Schema(description = ApiConstants.ITEMS_DESCRIPTION)
        private List<Item> items;

    }

    @Data
    @Builder
    public static class Item {

        @Schema(description = ApiConstants.DISH_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
        private String itemId;

        @Schema(description = ApiConstants.DISH_NAME_DESCRIPTION, example = ApiConstants.DISH_NAME_EXAMPLE)
        private String name;

        @Schema(description = ApiConstants.ITEM_SIZES_DESCRIPTION)
        private List<ItemSize> itemSizes;
    }

    @Data
    @Builder
    public static class ItemSize {

        @Schema(description = ApiConstants.SIZE_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
        private String sizeId;

        @Schema(description = ApiConstants.SIZE_NAME_DESCRIPTION, example = ApiConstants.SIZE_NAME_EXAMPLE)
        private String sizeName;

        @Schema(description = ApiConstants.PORTION_WEIGHT_DESCRIPTION, example = ApiConstants.PORTION_WEIGHT_DESCRIPTION)
        private double portionWeightGrams;

        @Schema(description = ApiConstants.MEASURE_UNIT_DESCRIPTION, example = ApiConstants.MEASURE_UNIT_EXAMPLE)
        private String measureUnitType;

        @Schema(description = ApiConstants.PRICE_DESCRIPTION, example = ApiConstants.PRICE_EXAMPLE)
        private double price;

        @Schema(description = ApiConstants.IMAGE_URL_DESCRIPTION, example = ApiConstants.IMAGE_URL_EXAMPLE)
        private String buttonImageUrl;

    }
}
