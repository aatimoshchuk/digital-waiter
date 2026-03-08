package nsu.sber.voiceassistant.pipelinr;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.NluResult;

import nsu.sber.voiceassistant.pipelinr.dispatch.IntentCommandFactory;
import nsu.sber.voiceassistant.pipelinr.dispatch.IntentCommandRegistry;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandIntentProcessor {

    private final Pipeline pipeline;
    private final IntentCommandRegistry registry;

    public ProcessingResponse process(NluResult nlu) {
        if (nlu == null || nlu.getIntent() == null) {
            return new ProcessingResponse(true, "Прошу повторить запрос.");
        }

        IntentCommandFactory factory = registry.get(nlu.getIntent());

        if (factory == null) {
            return new ProcessingResponse(true,
                    nlu.getResponse() != null ? nlu.getResponse() : "Не понял запрос.");
        }

        return factory.create(nlu).execute(pipeline);
    }
}