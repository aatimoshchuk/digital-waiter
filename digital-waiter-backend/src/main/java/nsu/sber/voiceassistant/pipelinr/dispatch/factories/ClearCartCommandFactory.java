package nsu.sber.voiceassistant.pipelinr.dispatch.factories;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.NluResult;
import nsu.sber.voiceassistant.pipelinr.dispatch.IntentCommandFactory;
import org.springframework.stereotype.Component;

@Component("clear_cart")
public class ClearCartCommandFactory implements IntentCommandFactory {
    @Override
    public Command<ProcessingResponse> create(NluResult nlu) {
        return new nsu.sber.voiceassistant.pipelinr.commands.clear.ClearCartCommand();
    }
}