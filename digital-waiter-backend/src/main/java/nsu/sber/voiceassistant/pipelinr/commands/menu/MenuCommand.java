package nsu.sber.voiceassistant.pipelinr.commands.menu;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

@PromptDesc(
        intent = IntentType.GET_MENU,
        description = "Показать пользователю меню ресторана"
)
public class MenuCommand implements Command<ProcessingResponse> {
}
