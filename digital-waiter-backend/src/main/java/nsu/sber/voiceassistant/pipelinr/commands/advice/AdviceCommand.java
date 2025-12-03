package nsu.sber.voiceassistant.pipelinr.commands.advice;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.voiceassistant.dto.ProcessingResponse;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdviceCommand implements Command<ProcessingResponse> {
    private Map<String, String> context;
}
