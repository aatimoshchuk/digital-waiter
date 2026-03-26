package nsu.sber.voiceassistant.pipelinr.commands.menu;

import an.awesome.pipelinr.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

import java.util.List;
import java.util.Map;

import static nsu.sber.voiceassistant.model.Intents.GET_MENU;

@Data
@EqualsAndHashCode(callSuper = false)
@PromptDesc(
        intent = GET_MENU,
        description = "Показать пользователю только подходящие блюда из меню",
        entitiesHint = "Учитывай пожелания пользователя: мясное, без мяса, сладкое, напитки, завтрак и т.д."
)
public class MenuCommand implements Command<ProcessingResponse> {
    private String text;
    private List<Map<String, String>> entities;
    private List<LlmMenuItem> items;
}