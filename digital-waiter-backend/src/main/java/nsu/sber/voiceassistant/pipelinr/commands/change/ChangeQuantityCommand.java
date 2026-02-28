package nsu.sber.voiceassistant.pipelinr.commands.change;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

@PromptDesc(
        intent = "change_quantity",
        description = "Изменить количество блюда в заказе",
        entitiesHint = """
      entities: [
        { "dish_name": "...", "quantity": 2 }
      ]
    """
)
public class ChangeQuantityCommand implements Command<ProcessingResponse> {
}
