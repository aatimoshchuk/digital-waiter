package nsu.sber.domain.model.cart;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public class Cart implements Serializable {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(ModifyCartItemRequest request) {
        for (CartItem cartItem : items) {
            if (isRequestAndCartItemsEquals(request, cartItem)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return;
            }
        }

        CartItem.CartItemBuilder cartItemBuilder = CartItem
                .builder()
                .dishId(request.getItemId())
                .quantity(1);

        if (request.getSizeId() != null) {
            cartItemBuilder.sizeId(request.getSizeId());
        }

        items.add(cartItemBuilder.build());
    }

    public void removeItem(ModifyCartItemRequest request) {
        Iterator<CartItem> iterator = items.iterator();

        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();

            if (isRequestAndCartItemsEquals(request, cartItem)) {
                if (cartItem.getQuantity() == 1) {
                    iterator.remove();
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }

                break;
            }
        }
    }

    private boolean isRequestAndCartItemsEquals(ModifyCartItemRequest request, CartItem cartItem) {
        if (request.getSizeId() == null || cartItem.getSizeId() == null) {
            return cartItem.getDishId().equals(request.getItemId());
        }
        return cartItem.getDishId().equals(request.getItemId()) && cartItem.getSizeId().equals(request.getSizeId());
    }
}
