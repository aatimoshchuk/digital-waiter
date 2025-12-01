package nsu.sber.govno.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.govno.application.CommandIntentProcessor;
import nsu.sber.govno.dto.ProcessingRequest;
import nsu.sber.govno.dto.ProcessingResponse;
import nsu.sber.govno.model.CommandIntent;
import nsu.sber.govno.model.IntentType;
import nsu.sber.govno.model.NluResult;
import nsu.sber.govno.service.integration.BackendClient;
import nsu.sber.govno.service.nlu.NluService;
import nsu.sber.govno.service.parser.CommandParser;
import nsu.sber.govno.service.stt.SttService;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingService {

    private final SttService sttService;
    private final NluService nluService;
    private final CommandParser commandParser;
    private final BackendClient backendClient;
    private final CommandIntentProcessor commandIntentProcessor;

    public ProcessingResponse processAudio(ProcessingRequest request) {
        log.info("Starting STT processing");
        String text = sttService.recognizeFile(request.getAudioFile());
        log.info("Transcribed text: {}", text == null ? "null" : text);
        NluResult nluResult = nluService.parse(text, request.getContext());
        return new ProcessingResponse(true, nluResult.getResponse());
//        CommandIntent command = commandParser.parse(nluResult);
//        ProcessingResponse response = backendClient.execute(command, "sessionId");
//        return response;
    }

    public ProcessingResponse processText(ProcessingRequest request) {
        NluResult nluResult = nluService.parse(request.getText(), request.getContext());

        return commandIntentProcessor.process(nluResult, "sessionId");
    }

}
