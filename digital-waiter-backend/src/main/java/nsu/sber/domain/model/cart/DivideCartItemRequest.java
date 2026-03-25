package nsu.sber.domain.model.cart;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DivideCartItemRequest extends CartItemRequest{

    private List<Integer> finalGuestNumbers;

}
