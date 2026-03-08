package nsu.sber.voiceassistant.pipelinr.dispatch.factories;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.NluResult;
import nsu.sber.voiceassistant.pipelinr.commands.menu.MenuCommand;
import nsu.sber.voiceassistant.pipelinr.dispatch.IntentCommandFactory;
import org.springframework.stereotype.Component;

@Component("get_menu")
public class MenuCommandFactory implements IntentCommandFactory {

    @Override
    public Command<ProcessingResponse> create(NluResult nlu) {
        return new MenuCommand();
    }
}