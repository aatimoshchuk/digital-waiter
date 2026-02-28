package nsu.sber.voiceassistant.pipelinr.commands.clear;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

import static nsu.sber.voiceassistant.model.Intents.CLEAR_CART;

@PromptDesc(
        intent = CLEAR_CART,
        description = "Очистить весь заказ пользователя"
)
public class ClearCartCommand implements Command<ProcessingResponse> {
}
