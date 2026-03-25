package nsu.sber.domain.model.cart;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CartResponse {

    private Integer guestCount;
    private List<CartItemResponse> cartItemResponseList;

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

        private double quantity;

        private int guestNumber;
    }

}
