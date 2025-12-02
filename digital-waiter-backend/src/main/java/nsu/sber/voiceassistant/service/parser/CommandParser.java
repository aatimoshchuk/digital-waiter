package nsu.sber.voiceassistant.service.parser;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import nsu.sber.voiceassistant.application.ResponseParser;
import nsu.sber.voiceassistant.model.CommandIntent;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Data
class CommandIntentResponse {
    private List<CommandIntent> commands;
}

@Service
@RequiredArgsConstructor
public class CommandParser {

    private final ResponseParser responseParser;

    public List<CommandIntent> parse(String nluResult) {
        try {
            CommandIntentResponse response =
                    responseParser.parse(nluResult, CommandIntentResponse.class);

            if (response == null || response.getCommands() == null) {
                return Collections.emptyList();
            }

            return response.getCommands();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse command intents", e);
        }
    }
}
