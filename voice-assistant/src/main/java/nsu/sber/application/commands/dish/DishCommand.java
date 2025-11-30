package nsu.sber.application.commands.dish;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.web.dto.MenuItemResponseDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishCommand implements Command<MenuItemResponseDto> {
    private String dishId;
}
