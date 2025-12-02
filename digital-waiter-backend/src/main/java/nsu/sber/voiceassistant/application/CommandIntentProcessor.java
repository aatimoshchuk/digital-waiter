package nsu.sber.voiceassistant.application;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import nsu.sber.voiceassistant.application.commands.add.AddItemCommand;

import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.model.NluResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandIntentProcessor {
    private final Pipeline pipeline;

    public ProcessingResponse process(NluResult nluResult, String sessionId) {
        IntentType type = nluResult.getIntent();

        return switch (type) {

            case ADD_ITEM -> handleAddItem(nluResult);
            case REMOVE_ITEM -> handleRemoveItem(nluResult);
            case CHANGE_QUANTITY -> handleChangeQuantity(nluResult);

            default -> new ProcessingResponse(true, nluResult.getResponse());
        };
    }

    private ProcessingResponse handleAddItem(NluResult nluResult) {
        //String itemText = (String)nluResult.getEntities().get("itemText");
        return new AddItemCommand(nluResult.getEntities()).execute(pipeline);
    }

    private ProcessingResponse handleRemoveItem(NluResult nluResult) {
        //String itemText = (String)nluResult.getEntities().get("itemText");
        //return new RemoveItemCommand(nluResult.getEntities()).execute(pipeline);
        return null;
    }

    private ProcessingResponse handleChangeQuantity(NluResult nluResult) {
//        String itemText = (String)nluResult.getParameters().get("item");
//        Integer quantity = Integer.valueOf((String)nluResult.getParameters().get("quantity"));

        return null;
    }
}
