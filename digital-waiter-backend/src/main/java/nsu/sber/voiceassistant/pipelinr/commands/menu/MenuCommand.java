package nsu.sber.voiceassistant.pipelinr.commands.menu;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

@PromptDesc(
        intent = "get_menu",
        description = "Показать пользователю меню ресторана"
)
public class MenuCommand implements Command<ProcessingResponse> {
}
