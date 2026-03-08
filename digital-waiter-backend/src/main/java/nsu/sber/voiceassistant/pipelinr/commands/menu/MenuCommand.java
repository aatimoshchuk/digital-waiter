package nsu.sber.voiceassistant.pipelinr.commands.menu;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

import static nsu.sber.voiceassistant.model.Intents.GET_MENU;

@PromptDesc(
        intent = GET_MENU,
        description = "Показать пользователю меню ресторана"
)
public class MenuCommand implements Command<ProcessingResponse> {
}
