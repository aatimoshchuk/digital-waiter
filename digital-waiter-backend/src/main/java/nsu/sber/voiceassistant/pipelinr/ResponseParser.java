package nsu.sber.voiceassistant.pipelinr;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseParser {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T parse(String content, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(content, clazz);
        } catch (Exception e) {
            log.error("Ошибка парсинга JSON от LLM: {}", content, e);
            return null;
        }
    }
}