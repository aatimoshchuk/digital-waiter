package nsu.sber.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddDishToCartRequestDto {

    @NotBlank
    private String dishId;

    private String sizeId;
}
