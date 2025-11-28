package nsu.sber.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.dto.ProcessingRequest;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.model.CommandIntent;
import nsu.sber.model.NluResult;
import nsu.sber.service.integration.BackendClient;
import nsu.sber.service.nlu.NluService;
import nsu.sber.service.parser.CommandParser;
import nsu.sber.service.stt.SttService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessingService {

    private final SttService sttService;
    private final NluService nluService;
    private final CommandParser commandParser;
    private final BackendClient backendClient;

    public ProcessingResponse processAudio(ProcessingRequest request) {
        //String text = sttService.recognizeFile(request.getAudioFile());
        NluResult nluResult = nluService.parse("заказать 2 пиццы и колу", request.getContext());
        // CommandIntent command = commandParser.parse(nluResult.getResponse());
        // ProcessingResponse response = backendClient.execute(command, "sessionId");
        return new ProcessingResponse(true, nluResult.getResponse());
    }

    public ProcessingResponse processText(ProcessingRequest request) {
        NluResult nluResult = nluService.parse(request.getText(), request.getContext());
        CommandIntent command = commandParser.parse(nluResult.getResponse());
        ProcessingResponse response = backendClient.execute(command, "sessionId");
        return response;
    }

}
