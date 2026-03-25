package nsu.sber.domain.model.cart;

import lombok.Data;

@Data
public class CartItemRequest {

    private String itemId;

    private String sizeId;

    private Integer currentGuestNumber;

}
