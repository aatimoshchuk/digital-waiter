package nsu.sber.govno.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseParser {

    private static ObjectMapper objectMapper;

    public static <T> T parse(String aiResult, Class<T> clazz) {
        try {

            return objectMapper.readValue(aiResult, clazz);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
