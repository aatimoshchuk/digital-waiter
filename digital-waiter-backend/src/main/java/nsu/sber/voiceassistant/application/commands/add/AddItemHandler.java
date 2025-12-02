package nsu.sber.voiceassistant.application.commands.add;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.CartService;

import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.web.mapper.CartDtoMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddItemHandler implements Command.Handler<AddItemCommand, ProcessingResponse> {

    private final CartService cartService;
    private final CartDtoMapper cartDtoMapper;
    @Override
    public ProcessingResponse handle(AddItemCommand command) {
        cartService.addItem(cartDtoMapper.dtoToModifyCartItemRequest(command.getModifyRequest()));
        return ProcessingResponse.builder()
                .success(true)
                .transcribedText("Item added successfully")
                .build();
    }
}
