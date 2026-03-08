package nsu.sber.voiceassistant.pipelinr.dispatch;

import an.awesome.pipelinr.Command;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.model.NluResult;

public interface IntentCommandFactory {
    Command<ProcessingResponse> create(NluResult nlu);
}