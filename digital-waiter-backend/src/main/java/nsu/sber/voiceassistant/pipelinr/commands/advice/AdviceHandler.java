package nsu.sber.voiceassistant.pipelinr.commands.advice;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nsu.sber.voiceassistant.dto.ProcessingResponse;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdviceHandler implements Command.Handler<AdviceCommand, ProcessingResponse> {

    @Override
    public ProcessingResponse handle(AdviceCommand command) {
        String advice = command.getContext().get("advice");

        return ProcessingResponse.builder()
                .success(true)
                .transcribedText(advice)
                .build();
    }
}
