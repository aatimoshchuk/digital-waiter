package nsu.sber.voiceassistant.pipelinr.commands.clear;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

@PromptDesc(
        intent = IntentType.CLEAR_CART,
        description = "Очистить весь заказ пользователя"
)
public class ClearCartCommand implements Command<ProcessingResponse> {
}
