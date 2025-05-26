package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import nsu.sber.web.dto.AddDishToCartRequestDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    @PostMapping("/add-dish-to-cart")
    @Operation(
            summary = "Добавление блюда в корзину",
            description = "Добавление блюда в корзину, привязанную к сессии пользователя"
    )
    public void addDishToCart(@RequestBody AddDishToCartRequestDto addDishToCartRequestDto) {

    }
}
