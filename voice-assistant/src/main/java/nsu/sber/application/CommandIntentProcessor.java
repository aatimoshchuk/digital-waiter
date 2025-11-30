package nsu.sber.application;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import nsu.sber.application.commands.add.AddItemCommand;
import nsu.sber.application.commands.remove.RemoveItemCommand;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.model.CommandIntent;
import nsu.sber.model.IntentType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandIntentProcessor {

    private final Pipeline pipeline;

    public ProcessingResponse process(CommandIntent cmd) {
        return processIntent(cmd);
    }

    private ProcessingResponse processIntent(CommandIntent cmd) {
        IntentType type = cmd.getType();

        return switch (type) {

            case ADD_ITEM -> handleAddItem(cmd);
            case REMOVE_ITEM -> handleRemoveItem(cmd);
            case CHANGE_QUANTITY -> handleChangeQuantity(cmd);

            default -> throw new IllegalArgumentException("Unknown command: " + type);
        };
    }


    private ProcessingResponse handleAddItem(CommandIntent cmd) {
        String itemText = (String)cmd.getParameters().get("item");
        return new AddItemCommand(itemText).execute(pipeline);
    }

    private ProcessingResponse handleRemoveItem(CommandIntent cmd) {
        String itemText = (String)cmd.getParameters().get("item");
        return new RemoveItemCommand(itemText).execute(pipeline);
    }

    private ProcessingResponse handleChangeQuantity(CommandIntent cmd) {
//        String itemText = (String)cmd.getParameters().get("item");
//        Integer quantity = Integer.valueOf((String)cmd.getParameters().get("quantity"));

        return null;
    }
}
