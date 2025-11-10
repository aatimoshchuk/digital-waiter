package nsu.sber.service.commands;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddItemHandler implements Command.Handler<AddItemCommand, Void> {
    //TODO: Реализовать хенделер для Добавления item-ов
    //private final CartService cartService;
    //private final CartDtoMapper cartDtoMapper;

    @Override
    public Void handle(AddItemCommand command) {
        //cartService.addItem(cartDtoMapper.dtoToModifyCartItemRequest(command.getItem()));
        return null;
    }
}