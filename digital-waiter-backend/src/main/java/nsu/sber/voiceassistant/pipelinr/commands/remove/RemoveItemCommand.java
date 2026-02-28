package nsu.sber.voiceassistant.pipelinr.commands.remove;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;
import nsu.sber.web.dto.ModifyCartItemRequestDto;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PromptDesc(
        intent = "remove_item",
        description = "Удалить блюдо из заказа",
        entitiesHint = """
      entities: [
        { "dish_name": "...", "size": "L" }
      ]
    """
)
public class RemoveItemCommand implements Command<ProcessingResponse> {
    private ModifyCartItemRequestDto modifyRequest;
    private String text;
    private List<Map<String, String>> entities;

    public RemoveItemCommand(String text, List<Map<String, String>> entities) {
        this.text = text;
        this.entities = entities;
    }
}