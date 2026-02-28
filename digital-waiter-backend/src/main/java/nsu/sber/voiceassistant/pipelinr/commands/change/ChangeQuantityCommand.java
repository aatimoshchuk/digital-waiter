package nsu.sber.voiceassistant.pipelinr.commands.change;

import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

@PromptDesc(
        intent = IntentType.CHANGE_QUANTITY,
        description = "Изменить количество блюда в заказе",
        entitiesHint = """
      entities: [
        { "dish_name": "...", "quantity": 2 }
      ]
    """
)
public class ChangeQuantityCommand {
}
