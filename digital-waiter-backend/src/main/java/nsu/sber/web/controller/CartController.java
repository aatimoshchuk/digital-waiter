package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.CartService;
import nsu.sber.web.dto.CartResponseDto;
import nsu.sber.web.dto.DivideCartItemRequestDto;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import nsu.sber.web.dto.TransferCartItemRequestDto;
import nsu.sber.web.mapper.CartDtoMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@Tag(name = "Cart Controller")
public class CartController {

    private final CartService cartService;
    private final CartDtoMapper cartDtoMapper;

    @PostMapping("/guests/add")
    @Operation(
            summary = "Add a new guest",
            description = "Adds a new guest"
    )
    public void addGuest() {
        cartService.addGuest();
    }

    @PostMapping("/items/add")
    @Operation(
            summary = "Add an item to the cart",
            description = "Adds one portion of the dish to the user's session-bound cart"
    )
    public void addItemToCart(@RequestBody @Valid ModifyCartItemRequestDto modifyCartItemRequestDto) {
        cartService.addItem(cartDtoMapper.dtoToModifyCartItemRequest(modifyCartItemRequestDto));
    }

    @PostMapping("/items/remove")
    @Operation(
            summary = "Remove an item from the cart",
            description = "Removes one portion of the dish from the user's session-bound cart"
    )
    public void removeItemFromCart(@RequestBody @Valid ModifyCartItemRequestDto modifyCartItemRequestDto) {
        cartService.removeItem(cartDtoMapper.dtoToModifyCartItemRequest(modifyCartItemRequestDto));
    }

    @PostMapping("/items/transfer")
    @Operation(
            summary = "Transfer the cart item to another guest",
            description = "Transfers the cart item from guest with currentGuestNumber to guest with finalGuestNumber"
    )
    public void transferItem(@RequestBody @Valid TransferCartItemRequestDto transferCartItemRequestDto) {
        cartService.transferItem(cartDtoMapper.dtoToTransferCartItemRequest(transferCartItemRequestDto));
    }

    @PostMapping("/items/divide")
    @Operation(
            summary = "Divide the item between guests",
            description = """
                    Divides the item equally between the guests whose numbers are indicated in the finalGuestsNumbers.
                    If the finalGuestsNumbers is empty, the item will be divided between all guests
                    """
    )
    public void divideItem(@RequestBody @Valid DivideCartItemRequestDto divideCartItemRequestDto) {
        cartService.divideItem(cartDtoMapper.dtoToDivideCartItemRequest(divideCartItemRequestDto));
    }

    @GetMapping
    @Operation(
            summary = "Get the cart",
            description = "Returns the list of dishes currently in the cart"
    )
    public CartResponseDto getCart() {
        return cartDtoMapper.cartResponseToDto(cartService.getCartResponse());
    }

    @DeleteMapping
    @Operation(
            summary = "Clear the cart",
            description = "Removes all items from the user's cart"
    )
    public void clearCart() {
        cartService.clearCart();
    }

}
