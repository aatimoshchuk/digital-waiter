package nsu.sber.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModifyCartItemRequestDto {

    @NotBlank
    private String itemId;

    private String sizeId;
}
