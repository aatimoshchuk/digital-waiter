package nsu.sber.domain.model.cart;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CartResponse {

    private List<CartItemResponse> cartItemResponseList = new ArrayList<>();

    @Data
    @Builder
    public static class CartItemResponse {
        private String itemId;

        private String name;

        private String sizeId;

        private double portionWeightGrams;

        private String measureUnitType;

        private double price;

        private String buttonImageUrl;

        private int quantity;
    }

}
