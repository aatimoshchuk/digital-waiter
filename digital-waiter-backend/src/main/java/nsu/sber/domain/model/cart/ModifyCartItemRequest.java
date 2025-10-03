package nsu.sber.domain.model.cart;

import lombok.Data;

@Data
public class ModifyCartItemRequest {

    private String itemId;

    private String sizeId;

}
