package nsu.sber.voiceassistant.pipelinr.commands.remove;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;
import nsu.sber.web.dto.ModifyCartItemRequestDto;

@PromptDesc(
        intent = IntentType.REMOVE_ITEM,
        description = "Удалить блюдо из заказа",
        entitiesHint = """
      entities: [
        { "dish_name": "...", "size": "L" }
      ]
    """
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveItemCommand implements Command<ProcessingResponse> {
    private ModifyCartItemRequestDto modifyRequest;
    private String userText;

    public RemoveItemCommand(String userText) {
        this.userText = userText;
    }
}
