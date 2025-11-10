package nsu.sber.service;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import nsu.sber.model.CommandIntent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntentExecutor {

    private final Pipeline pipeline;

    public Object execute(CommandIntent intent) {
//        switch (intent.getType()) {
//            case ADD_ITEM:
//                ModifyCartItemRequestDto addItemDto =
//                        (ModifyCartItemRequestDto) intent.getParameters().get("item");
//                return new AddItemCommand(addItemDto).execute(pipeline);
//
//            case REMOVE_ITEM:
//                ModifyCartItemRequestDto removeItemDto =
//                        (ModifyCartItemRequestDto) intent.getParameters().get("item");
//                return new RemoveItemCommand(removeItemDto).execute(pipeline);
//
//            case SHOW_CART:
//                return new ShowCartCommand().execute(pipeline);
//
//            case CANCEL:
//                return new ClearCartCommand().execute(pipeline);
//
//            default:
//                throw new IllegalArgumentException("Unknown intent: " + intent.getType());
//        }
//    }
}