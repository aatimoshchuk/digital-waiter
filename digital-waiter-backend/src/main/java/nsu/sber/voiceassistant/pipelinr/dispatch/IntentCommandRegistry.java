package nsu.sber.voiceassistant.pipelinr.dispatch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class IntentCommandRegistry {

    private final Map<String, IntentCommandFactory> factories;

    public IntentCommandFactory get(String intent) {
        return factories.get(intent);
    }
}