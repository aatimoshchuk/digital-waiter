package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.util.ApiConstants;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponseDto {

    @Schema(description = "Items in the cart")
    private List<CartResponse.CartItemResponse> cartItemResponseList = new ArrayList<>();

    @Data
    public static class CartItemResponse {

        @Schema(description = ApiConstants.DISH_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
        private String itemId;

        @Schema(description = ApiConstants.DISH_NAME_DESCRIPTION, example = ApiConstants.DISH_NAME_EXAMPLE)
        private String name;

        @Schema(description = ApiConstants.SIZE_ID_DESCRIPTION, example = ApiConstants.POS_ID_EXAMPLE)
        private String sizeId;

        @Schema(description = ApiConstants.PORTION_WEIGHT_DESCRIPTION, example = ApiConstants.PORTION_WEIGHT_EXAMPLE)
        private double portionWeightGrams;

        @Schema(description = ApiConstants.MEASURE_UNIT_DESCRIPTION, example = ApiConstants.MEASURE_UNIT_EXAMPLE)
        private String measureUnitType;

        @Schema(description = ApiConstants.PRICE_DESCRIPTION, example = ApiConstants.PRICE_EXAMPLE)
        private double price;

        @Schema(description = ApiConstants.IMAGE_URL_DESCRIPTION, example = ApiConstants.IMAGE_URL_EXAMPLE)
        private String buttonImageUrl;

        @Schema(description = ApiConstants.QUANTITY_DESCRIPTION, example = ApiConstants.QUANTITY_EXAMPLE)
        private int quantity;
    }
}
