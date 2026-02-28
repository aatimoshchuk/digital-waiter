package nsu.sber.voiceassistant.pipelinr.commands.advice;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

import java.util.Map;

@PromptDesc(
        intent = IntentType.GET_ADVICE,
        description = "Дать пользователю рекомендацию блюда на основе контекста или меню",
        entitiesHint = """
      context: предпочтения пользователя, например:
      { "taste": "острое", "type": "суп" }
    """
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdviceCommand implements Command<ProcessingResponse> {
    private Map<String, String> context;
}
