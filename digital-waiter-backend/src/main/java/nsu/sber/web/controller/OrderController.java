package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nsu.sber.web.dto.AddDishToCartRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Заказы", description = "API для управления заказами")
public class OrderController {

    @PostMapping("/add-dish-to-cart")
    @Operation(
            summary = "Добавление блюда в корзину",
            description = "Добавление блюда в корзину, привязанную к сессии пользователя"
    )
    @Hidden
    public void addDishToCart(@RequestBody AddDishToCartRequestDto addDishToCartRequestDto) {

    }
}
