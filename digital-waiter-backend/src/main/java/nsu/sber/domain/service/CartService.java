package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.cart.Cart;
import nsu.sber.domain.model.cart.CartItem;
import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.cart.ModifyCartItemRequest;
import nsu.sber.domain.model.entity.RestaurantTable;
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

    private final MenuService menuService;
    private final RestaurantTableService restaurantTableService;

    public void addItem(ModifyCartItemRequest modifyCartItemRequest) {
        if (!menuService.existsItemById(modifyCartItemRequest.getItemId())) {
            throw new DigitalWaiterException.DishNotFoundException(modifyCartItemRequest.getItemId());
        }

        if (modifyCartItemRequest.getSizeId() != null &&
                !menuService.existsItemSizeById(modifyCartItemRequest.getItemId(), modifyCartItemRequest.getSizeId())) {
            throw new DigitalWaiterException.SizeNotFoundException(
                    modifyCartItemRequest.getSizeId(),
                    modifyCartItemRequest.getItemId()
            );
        }

        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();

        Cart cart = getCart(restaurantTable.getPosTableId());
        cart.addItem(modifyCartItemRequest);

        cartRepository.save(restaurantTable.getPosTableId(), cart);
    }

    public void removeItem(ModifyCartItemRequest modifyCartItemRequest) {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();

        Cart cart = getCart(restaurantTable.getPosTableId());
        cart.removeItem(modifyCartItemRequest);

        cartRepository.save(restaurantTable.getPosTableId(), cart);
    }

    public void clearCart() {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();

        cartRepository.delete(restaurantTable.getPosTableId());
    }

    public void clearCartByTableId(String tableId) {
        cartRepository.delete(tableId);
    }

    public boolean isCartEmpty() {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();

        return cartRepository.findByTableId(restaurantTable.getPosTableId())
                .map(Cart::getItems)
                .map(List::isEmpty)
                .orElse(true);
    }
    
    public CartResponse getCartResponse() {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();

        return cartRepository.findByTableId(restaurantTable.getPosTableId())
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

    private Cart getCart(String posTableId) {
        return cartRepository.findByTableId(posTableId)
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
