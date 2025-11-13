package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.cart.Cart;
import nsu.sber.domain.model.cart.CartItem;
import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.cart.ModifyCartItemRequest;
import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.MenuItem;
import nsu.sber.domain.port.repository.redis.CartRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepositoryPort cartRepository;
    private final RequestContextProvider requestContextProvider;
    private final MenuService menuService;

    public void addItem(ModifyCartItemRequest modifyCartItemRequest) {
        Cart cart = getCart();
        cart.addItem(modifyCartItemRequest);

        cartRepository.save(requestContextProvider.getTableId(), cart);
    }

    public void removeItem(ModifyCartItemRequest modifyCartItemRequest) {
        Cart cart = getCart();
        cart.removeItem(modifyCartItemRequest);

        cartRepository.save(requestContextProvider.getTableId(), cart);
    }

    public void clearCart() {
        cartRepository.delete(requestContextProvider.getTableId());
    }

    public void clearCartByTableId(String tableId) {
        cartRepository.delete(tableId);
    }

    public boolean isCartEmpty() {
        return cartRepository.findByTableId(requestContextProvider.getTableId())
                .map(Cart::getItems)
                .map(List::isEmpty)
                .orElse(true);
    }
    
    public CartResponse getCartResponse() {
        return cartRepository.findByTableId(requestContextProvider.getTableId())
                .map(this::mapCartToCartResponse)
                .orElse(CartResponse.builder().cartItemResponseList(List.of()).build());
    }

    private CartResponse mapCartToCartResponse(Cart cart) {
        Menu menu = menuService.getMenu();

        List<CartResponse.CartItemResponse> items = cart.getItems().stream()
                .map(cartItem -> cartItemToCartItemResponse(cartItem, menu))
                .filter(Objects::nonNull)
                .toList();

        return CartResponse.builder()
                .cartItemResponseList(items)
                .build();
    }

    private Cart getCart() {
        return cartRepository.findByTableId(requestContextProvider.getTableId())
                .orElse(new Cart());
    }

    private CartResponse.CartItemResponse cartItemToCartItemResponse(CartItem cartItem, Menu menu) {
        MenuItem menuItem = menuService.findItemById(menu, cartItem.getDishId())
                .orElseThrow(() -> new DigitalWaiterException.DishNotFoundException(cartItem.getDishId()));

        MenuItem.ItemSize itemSize = getItemSize(cartItem, menuItem);

        return CartResponse.CartItemResponse
                .builder()
                .itemId(cartItem.getDishId())
                .name(menuItem.getName())
                .quantity(cartItem.getQuantity())
                .sizeId(cartItem.getSizeId())
                .price(itemSize.getPrices().get(0).getPrice())
                .portionWeightGrams(itemSize.getPortionWeightGrams())
                .measureUnitType(itemSize.getMeasureUnitType())
                .buttonImageUrl(itemSize.getButtonImageUrl())
                .build();
    }

    private MenuItem.ItemSize getItemSize(CartItem cartItem, MenuItem menuItem) {
        if (cartItem.getSizeId() != null) {
            return menuItem.getItemSizes().stream()
                    .filter(size -> size.getSizeId().equals(cartItem.getSizeId()))
                    .findFirst()
                    .orElse(null);
        }

        if (menuItem.getItemSizes().size() == 1 && menuItem.getItemSizes().get(0).getSizeId() == null) {
            return menuItem.getItemSizes().get(0);
        } else {
            return null;
        }
    }
}
