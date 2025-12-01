package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import nsu.sber.util.ApiConstants;

import java.util.List;
@Data
@Builder
public class MenuItemResponseDto {

    @Schema(description = ApiConstants.DISH_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
    private String itemId;

    @Schema(description = ApiConstants.DISH_NAME_DESCRIPTION, example = ApiConstants.DISH_NAME_EXAMPLE)
    private String name;

    @Schema(description = ApiConstants.DISH_DESCRIPTION_DESCRIPTION)
    private String description;

    @Schema(description = ApiConstants.ALLERGENS_DESCRIPTION)
    private List<Allergen> allergens;

    @Schema(description = ApiConstants.ITEM_SIZES_DESCRIPTION)
    private List<ItemSize> itemSizes;

    @Data
    @Builder
    public static class Allergen {

        @Schema(description = ApiConstants.ALLERGEN_NAME_DESCRIPTION)
        private String name;

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

        @Schema(description = ApiConstants.NUTRITION_DESCRIPTION)
        private NutritionPerHundredGrams nutritionPerHundredGrams;

    }

    @Data
    @Builder
    public static class NutritionPerHundredGrams {

        @Schema(description = ApiConstants.NUTRITION_FATS_DESCRIPTION)
        private double fats;

        @Schema(description = ApiConstants.NUTRITION_PROTEINS_DESCRIPTION)
        private double proteins;

        @Schema(description = ApiConstants.NUTRITION_CARBS_DESCRIPTION)
        private double carbs;

        @Schema(description = ApiConstants.NUTRITION_ENERGY_DESCRIPTION)
        private double energy;

    }
}
