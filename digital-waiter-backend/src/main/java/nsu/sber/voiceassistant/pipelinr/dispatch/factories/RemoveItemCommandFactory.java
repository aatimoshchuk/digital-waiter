package nsu.sber.voiceassistant.pipelinr.dispatch.factories;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.NluResult;
import nsu.sber.voiceassistant.pipelinr.commands.remove.RemoveItemCommand;
import nsu.sber.voiceassistant.pipelinr.dispatch.IntentCommandFactory;
import org.springframework.stereotype.Component;

@Component("remove_item")
public class RemoveItemCommandFactory implements IntentCommandFactory {
    @Override
    public Command<ProcessingResponse> create(NluResult nlu) {
        return new RemoveItemCommand(nlu.getText(), nlu.getEntities());
    }
}