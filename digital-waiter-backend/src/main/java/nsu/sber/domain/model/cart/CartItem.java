package nsu.sber.domain.model.cart;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@Jacksonized
public class CartItem implements Serializable {

    private String dishId;

    private String sizeId;

    private int quantity;

}
