package nsu.sber.voiceassistant.pipelinr.commands.add;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;
import nsu.sber.web.dto.ModifyCartItemRequestDto;

import java.util.List;
import java.util.Map;

@PromptDesc(
        intent = IntentType.ADD_ITEM,
        description = "Добавить блюдо(а) в заказ",
        entitiesHint = """
      entities: [{ "dish_name": "...", "quantity": 1 }]
    """
)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemCommand implements Command<ProcessingResponse> {
    private List<ModifyCartItemRequestDto> modifyRequest;
    private List<Map<String, String>> entities;

    public AddItemCommand(List<Map<String, String>> entities) {
        this.entities = entities;
    }
}
