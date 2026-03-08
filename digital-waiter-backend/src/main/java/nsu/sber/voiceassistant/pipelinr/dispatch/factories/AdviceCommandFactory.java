package nsu.sber.voiceassistant.pipelinr.dispatch.factories;

import an.awesome.pipelinr.Command;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.NluResult;
import nsu.sber.voiceassistant.pipelinr.commands.advice.AdviceCommand;
import nsu.sber.voiceassistant.pipelinr.dispatch.IntentCommandFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component("get_advice")
public class AdviceCommandFactory implements IntentCommandFactory {

    @Override
    public Command<ProcessingResponse> create(NluResult nlu) {
        String text = nlu == null ? null : nlu.getText();

        log.info("[AdviceCommandFactory] text='{}', entities={}", text, nlu == null ? null : nlu.getEntities());

        Map<String, String> ctx = (text == null || text.isBlank())
                ? null
                : Map.of("user_text", text);

        return new AdviceCommand(ctx);
    }
}