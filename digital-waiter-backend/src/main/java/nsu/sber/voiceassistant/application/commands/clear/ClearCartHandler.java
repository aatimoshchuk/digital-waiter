package nsu.sber.voiceassistant.application.commands.clear;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.CartService;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClearCartHandler implements Command.Handler<ClearCartCommand, ProcessingResponse> {

    private final CartService cartService;

    @Override
    public ProcessingResponse handle(ClearCartCommand command) {

        cartService.clearCart();

        return ProcessingResponse.builder()
                .success(true)
                .transcribedText("Cart cleared")
                .build();
    }
}
