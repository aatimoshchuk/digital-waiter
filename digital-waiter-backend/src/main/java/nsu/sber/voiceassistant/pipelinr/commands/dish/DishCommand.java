package nsu.sber.voiceassistant.pipelinr.commands.dish;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.prompt.PromptDesc;

import java.util.List;
import java.util.Map;

import static nsu.sber.voiceassistant.model.Intents.GET_INFO_DISH;

@PromptDesc(
        intent = GET_INFO_DISH,
        description = "Получить подробную информацию о блюде",
        entitiesHint = """
      entities: [
        { "dish_name": "Пицца Маргарита" }
      ]
    """
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishCommand implements Command<ProcessingResponse> {
    private List<ModifyCartItemRequestDtoCustom> items;
    private List<Map<String, String>> entities;

    public DishCommand(List<Map<String, String>> entities) {
        this.entities = entities;
    }
}
