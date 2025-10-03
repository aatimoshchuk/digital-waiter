package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.DishInfoResponse;
import nsu.sber.domain.model.cart.Cart;
import nsu.sber.domain.model.cart.CartItem;
import nsu.sber.domain.model.cart.CartResponse;
import nsu.sber.domain.model.cart.ModifyCartItemRequest;
import nsu.sber.domain.port.CartRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepositoryPort cartRepository;
    private final RequestContextProvider requestContextProvider;
    private final DishService dishService;

    public void addItem(ModifyCartItemRequest modifyCartItemRequest) {
        Cart cart = getCartByTableId();
        cart.addItem(modifyCartItemRequest);

        cartRepository.save(requestContextProvider.getTableId(), cart);
    }

    public void removeItem(ModifyCartItemRequest modifyCartItemRequest) {
        Cart cart = getCartByTableId();
        cart.removeItem(modifyCartItemRequest);

        cartRepository.save(requestContextProvider.getTableId(), cart);
    }

    public void clearCart() {
        cartRepository.delete(requestContextProvider.getTableId());
    }
    
    public CartResponse getCart() {
        Cart cart = cartRepository.findByTableId(requestContextProvider.getTableId());

        if (cart == null) {
            return CartResponse.builder().build();
        }

        List<CartItem> cartItems = cart.getItems();
        List<CartResponse.CartItemResponse> cartItemResponseList = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            CartResponse.CartItemResponse cartItemResponse = cartItemToCartItemResponse(cartItem);

            if (cartItemResponse == null) {
                continue;
            }

            cartItemResponseList.add(cartItemResponse);
        }

        return CartResponse.builder().cartItemResponseList(cartItemResponseList).build();
    }

    private Cart getCartByTableId() {
        Cart cart = cartRepository.findByTableId(requestContextProvider.getTableId());

        if (cart == null) {
            cart = new Cart();
        }

        return cart;
    }

    private CartResponse.CartItemResponse cartItemToCartItemResponse(CartItem cartItem) {
        DishInfoResponse dishInfoResponse = dishService.getDishInfo(cartItem.getDishId());
        DishInfoResponse.ItemSize itemSize;

        if (cartItem.getSizeId() == null) {
            if (dishInfoResponse.getItemSizes().size() == 1 && dishInfoResponse.getItemSizes().get(0).getSizeId() == null) {
                itemSize = dishInfoResponse.getItemSizes().get(0);
            } else {
                return null;
            }
        } else {
            Optional<DishInfoResponse.ItemSize> itemSizeOpt = dishInfoResponse.getItemSizes().stream()
                    .filter(size -> size.getSizeId().equals(cartItem.getSizeId()))
                    .findFirst();

            if (itemSizeOpt.isEmpty()) {
                return null;
            }

            itemSize = itemSizeOpt.get();
        }

        return CartResponse.CartItemResponse
                .builder()
                .itemId(cartItem.getDishId())
                .name(dishInfoResponse.getName())
                .quantity(cartItem.getQuantity())
                .sizeId(cartItem.getSizeId())
                .price(itemSize.getPrice())
                .portionWeightGrams(itemSize.getPortionWeightGrams())
                .measureUnitType(itemSize.getMeasureUnitType())
                .buttonImageUrl(itemSize.getButtonImageUrl())
                .build();
    }
}
