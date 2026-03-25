package nsu.sber.domain.model.cart;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart implements Serializable {
    private Integer guestCount = 1;
    private List<CartItem> items = new ArrayList<>();

    public void addGuest() {
        guestCount++;
    }

    public boolean isGuestExists(CartItemRequest request) {
        if (request.getCurrentGuestNumber() != null && request.getCurrentGuestNumber() > guestCount) {
            return false;
        }

        if (request instanceof TransferCartItemRequest transferRequest) {
            return transferRequest.getFinalGuestNumber() == null || transferRequest.getFinalGuestNumber() <= guestCount;
        } else if (request instanceof DivideCartItemRequest divideRequest) {
            for (Integer guestNumber : divideRequest.getFinalGuestNumbers()) {
                if (guestNumber != null && guestNumber > guestCount) {
                    return false;
                }
            }
        }

        return true;
    }

    public void addItem(CartItemRequest request, double quantity) {
        for (CartItem cartItem : items) {
            if (isRequestAndCartItemsEquals(request, cartItem)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }

        CartItem.CartItemBuilder cartItemBuilder = CartItem
                .builder()
                .dishId(request.getItemId())
                .quantity(quantity)
                .guestNumber(request.getCurrentGuestNumber() == null ? 0 : request.getCurrentGuestNumber());

        if (request.getSizeId() != null) {
            cartItemBuilder.sizeId(request.getSizeId());
        }

        items.add(cartItemBuilder.build());
    }

    public void removeItem(ModifyCartItemRequest request) {
        for (CartItem cartItem : items) {
            if (isRequestAndCartItemsEquals(request, cartItem)) {
                if (cartItem.getQuantity() == 1) {
                    items.remove(cartItem);
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }

                return;
            }
        }
    }

    public void transferItem(TransferCartItemRequest request) {
        for (CartItem cartItem : items) {
            if (isRequestAndCartItemsEquals(request, cartItem)) {
                items.remove(cartItem);

                request.setCurrentGuestNumber(request.getFinalGuestNumber());
                addItem(request, cartItem.getQuantity());

                return;
            }
        }
    }

    public void divideItem(DivideCartItemRequest request) {
        for (CartItem cartItem : items) {
            if (isRequestAndCartItemsEquals(request, cartItem)) {
                items.remove(cartItem);

                if (request.getFinalGuestNumbers().isEmpty()) {
                    double finalQuantity = cartItem.getQuantity() / guestCount;

                    for (int guestNumber = 1; guestNumber <= guestCount; guestNumber++) {
                        addDividedItem(request, guestNumber, finalQuantity);
                    }
                } else {
                    double finalQuantity = cartItem.getQuantity() / request.getFinalGuestNumbers().size();

                    for (Integer guestNumber : request.getFinalGuestNumbers()) {
                        addDividedItem(request, guestNumber, finalQuantity);
                    }
                }

                return;
            }
        }
    }

    private void addDividedItem(DivideCartItemRequest request, Integer guestNumber, double quantity) {
        for (CartItem cartItem : items) {
            if (isRequestAndCartItemsEquals(guestNumber, request.getSizeId(), request.getItemId(), cartItem)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }

        CartItem.CartItemBuilder cartItemBuilder = CartItem
                .builder()
                .dishId(request.getItemId())
                .quantity(quantity)
                .guestNumber(guestNumber == null ? 0 : guestNumber);

        if (request.getSizeId() != null) {
            cartItemBuilder.sizeId(request.getSizeId());
        }

        items.add(cartItemBuilder.build());
    }

    private boolean isRequestAndCartItemsEquals(CartItemRequest request, CartItem cartItem) {
        int requestGuestNumber = request.getCurrentGuestNumber() == null ? 0 : request.getCurrentGuestNumber();

        if (request.getSizeId() == null || cartItem.getSizeId() == null) {
            return cartItem.getDishId().equals(request.getItemId()) && cartItem.getGuestNumber() == requestGuestNumber;
        }
        return cartItem.getDishId().equals(request.getItemId()) && cartItem.getSizeId().equals(request.getSizeId()) &&
                cartItem.getGuestNumber() == requestGuestNumber;
    }

    private boolean isRequestAndCartItemsEquals(Integer guestNumber, String sizeId, String itemId, CartItem cartItem) {
        int requestGuestNumber = guestNumber == null ? 0 : guestNumber;

        if (sizeId == null || cartItem.getSizeId() == null) {
            return cartItem.getDishId().equals(itemId) && cartItem.getGuestNumber() == requestGuestNumber;
        }
        return cartItem.getDishId().equals(itemId) && cartItem.getSizeId().equals(sizeId) &&
                cartItem.getGuestNumber() == requestGuestNumber;
    }
}
