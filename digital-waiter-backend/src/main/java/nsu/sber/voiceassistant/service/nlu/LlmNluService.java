package nsu.sber.voiceassistant.service.nlu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.service.prompt.PromptFactory;
import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import nsu.sber.voiceassistant.model.NluResult;
import nsu.sber.voiceassistant.service.nlu.provider.GigaChatProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmNluService implements NluService {
    private final GigaChatProvider llmProvider;
    private final ObjectMapper objectMapper;

    @Override
    public NluResult parse(String text, String context) {
        log.info("Input text: '{}'", text);
        log.info("Context: '{}'", context != null ? context : "none");
        try {
            LlmRequest request = buildRequest(text, context);
            LlmResponse response = llmProvider.complete(request);
            if (response.isSuccess()) {
                log.info("LLM response received successfully in {} ms", response.getResponseTimeMs());
                log.info("LlmResponse content: '{}', errors: '{}'", response.getContent(), response.getError());
                NluResult result = parseResponse(response.getContent(), text);
                log.info("Parsed response: {}", result.getResponse());
                return result;
            }
            log.error("LLM response failed: {}", response.getContent());
            return NluResult.error(text);
        } catch (Exception e) {
            log.error("NLU parsing failed for text: '{}'. Error: {}", text, e.getMessage(), e);
            return NluResult.error(text);
        }
    }

    private LlmRequest buildRequest(String text, String context) {
        return LlmRequest.builder()
                .prompt(PromptFactory.BuildPromptMappingCommand())
                .message(text)
                .build();
    }

    private NluResult parseResponse(String content, String text) {
        try {
            String jsonContent = extractJson(content);
            NluResult result = objectMapper.readValue(jsonContent, NluResult.class);
            result.setText(text);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String extractJson(String content) {
        int startIdx = content.indexOf('{');
        int endIdx = content.lastIndexOf('}');

        if (startIdx != -1 && endIdx != -1 && endIdx > startIdx) {
            return content.substring(startIdx, endIdx + 1);
        }

        return content;
    }

}