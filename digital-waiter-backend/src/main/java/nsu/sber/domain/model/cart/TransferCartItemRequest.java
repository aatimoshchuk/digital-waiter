package nsu.sber.domain.model.cart;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransferCartItemRequest extends CartItemRequest {

    private Integer finalGuestNumber;

}
