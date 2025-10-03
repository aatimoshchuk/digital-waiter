package nsu.sber.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nsu.sber.domain.model.cart.CartResponse;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartResponseDto {
    private List<CartResponse.CartItemResponse> cartItemResponseList = new ArrayList<>();

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
