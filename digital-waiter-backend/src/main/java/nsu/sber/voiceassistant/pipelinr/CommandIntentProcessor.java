package nsu.sber.voiceassistant.pipelinr;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.IntentType;
import nsu.sber.voiceassistant.model.NluResult;

import nsu.sber.voiceassistant.pipelinr.commands.add.AddItemCommand;
import nsu.sber.voiceassistant.pipelinr.commands.advice.AdviceCommand;
import nsu.sber.voiceassistant.pipelinr.commands.dish.DishCommand;
import nsu.sber.voiceassistant.pipelinr.commands.menu.MenuCommand;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandIntentProcessor {

    private final Pipeline pipeline;

    public ProcessingResponse process(NluResult nlu, String sessionId) {
        if (nlu == null) {
            log.warn("[CommandIntentProcessor] NLU result is null — returning fallback response");
            return new ProcessingResponse(true, "прошу повторить ваш запрос.");
        }
        IntentType intent = nlu.getIntent();
        log.info("[CommandIntentProcessor] Intent: {}", intent);

        return switch (intent) {
            case ADD_ITEM         -> executeAddItem(nlu);
            case REMOVE_ITEM      -> executeRemoveItem(nlu);
            case CHANGE_QUANTITY  -> executeChangeQuantity(nlu);
            case GET_MENU         -> executeMenu();
            case GET_INFO_DISH    -> executeDishInfo(nlu);
            case GET_ADVICE       -> executeAdvice();
            default -> {
                log.info("[CommandIntentProcessor] Intent без команды → отвечаем текстом из NLU");
                yield new ProcessingResponse(true, nlu.getResponse());
            }
        };
    }

    private ProcessingResponse executeAdvice() {
        log.debug("[CommandIntentProcessor] Executing AdviceCommand");
        return new AdviceCommand().execute(pipeline);
    }

    private ProcessingResponse executeMenu() {
        log.debug("[CommandIntentProcessor] Executing MenuCommand");
        return new MenuCommand().execute(pipeline);
    }

    private ProcessingResponse executeDishInfo(NluResult nlu) {
        log.debug("[CommandIntentProcessor] Executing DishCommand with entities: {}", nlu.getEntities());
        return new DishCommand(nlu.getEntities()).execute(pipeline);
    }

    private ProcessingResponse executeAddItem(NluResult nlu) {
        log.debug("[CommandIntentProcessor] Executing AddItemCommand with entities: {}", nlu.getEntities());
        return new AddItemCommand(nlu.getEntities()).execute(pipeline);
    }

    private ProcessingResponse executeRemoveItem(NluResult nlu) {
        log.debug("[CommandIntentProcessor] RemoveItem not implemented");
        return new ProcessingResponse(false, "Удаление блюд пока недоступно.");
    }

    private ProcessingResponse executeChangeQuantity(NluResult nlu) {
        log.debug("[CommandIntentProcessor] ChangeQuantity not implemented");
        return new ProcessingResponse(false, "Изменение количества пока недоступно.");
    }
}
