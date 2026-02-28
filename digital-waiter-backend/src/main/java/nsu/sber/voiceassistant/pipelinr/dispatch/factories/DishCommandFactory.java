package nsu.sber.voiceassistant.pipelinr.dispatch.factories;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.NluResult;
import nsu.sber.voiceassistant.pipelinr.commands.dish.DishCommand;
import nsu.sber.voiceassistant.pipelinr.dispatch.IntentCommandFactory;
import org.springframework.stereotype.Component;

@Component("get_info_dish")
public class DishCommandFactory implements IntentCommandFactory {

    @Override
    public Command<ProcessingResponse> create(NluResult nlu) {
        return new DishCommand(nlu.getEntities());
    }
}