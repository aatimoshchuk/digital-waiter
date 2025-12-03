package nsu.sber.voiceassistant.pipelinr;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parse(String aiResult, Class<T> clazz) {
        try {

            return objectMapper.readValue(aiResult, clazz);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
