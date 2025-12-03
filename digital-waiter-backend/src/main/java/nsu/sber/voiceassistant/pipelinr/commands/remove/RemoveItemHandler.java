package nsu.sber.voiceassistant.pipelinr.commands.remove;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.CartService;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.web.mapper.CartDtoMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveItemHandler implements Command.Handler<RemoveItemCommand, ProcessingResponse> {

    private final CartService cartService;
    private final CartDtoMapper cartDtoMapper;

    @Override
    public ProcessingResponse handle(RemoveItemCommand command) {

        cartService.removeItem(
                cartDtoMapper.dtoToModifyCartItemRequest(command.getModifyRequest())
        );

        return ProcessingResponse.builder()
                .success(true)
                .transcribedText("Item removed successfully")
                .build();
    }
}
