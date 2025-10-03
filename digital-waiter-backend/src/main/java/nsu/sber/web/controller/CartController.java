package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.CartService;
import nsu.sber.web.dto.CartResponseDto;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import nsu.sber.web.mapper.CartDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Корзина", description = "API для управления корзиной")
public class CartController {
    private final CartService cartService;
    private final CartDtoMapper cartDtoMapper;

    @PostMapping("/add-item-to-cart")
    @Operation(
            summary = "Добавление блюда в корзину",
            description = "Добавление блюда в корзину, привязанную к сессии пользователя"
    )
    public void addItemToCart(@RequestBody ModifyCartItemRequestDto modifyCartItemRequestDto) {
        cartService.addItem(cartDtoMapper.dtoToModifyCartItemRequest(modifyCartItemRequestDto));
    }

    @PostMapping("/remove-item-from-cart")
    @Operation(
            summary = "Удаление блюда из корзины",
            description = "Удаление блюда из корзины, привязанную к сессии пользователя"
    )
    public void removeItemFromCart(@RequestBody ModifyCartItemRequestDto modifyCartItemRequestDto) {
        cartService.removeItem(cartDtoMapper.dtoToModifyCartItemRequest(modifyCartItemRequestDto));
    }

    @GetMapping("/cart")
    @Operation(
            summary = "Получение корзины",
            description = "Получение списка блюд, находящихся в корзине"
    )
    public CartResponseDto getCart() {
        return cartDtoMapper.cartResponseToDto(cartService.getCart());
    }

    @PostMapping("/clear-cart")
    @Operation(
            summary = "Очистка корзины",
            description = "Удаление всех позиций из корзины"
    )
    public void clearCart() {
        cartService.clearCart();
    }
}
