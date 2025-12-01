package nsu.sber.govno.application;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import nsu.sber.govno.application.commands.add.AddItemCommand;
import nsu.sber.govno.application.commands.remove.RemoveItemCommand;

import nsu.sber.govno.dto.ProcessingResponse;
import nsu.sber.govno.model.CommandIntent;
import nsu.sber.govno.model.IntentType;
import nsu.sber.govno.model.NluResult;
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
            // Значит не смогли распознать команду
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
