package nsu.sber.service.nlu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nsu.sber.dto.LlmRequest;
import nsu.sber.dto.LlmResponse;
import nsu.sber.model.NluResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmNluService implements NluService {
    private final AbstractLlmProvider llmProvider;
    private final ObjectMapper objectMapper;

    @Override
    public NluResult parse(String text, String context) {
        try {
            LlmRequest request = buildRequest(text, context);
            LlmResponse response = llmProvider.complete(request);
            if (response.isSuccess()) {
                NluResult result = parseResponse(response.getContent(), text);
                return result;
            }
            return NluResult.error(text);
        } catch (Exception e) {
            return NluResult.error(text);
        }
    }

    private LlmRequest buildRequest(String text, String context) {
        return LlmRequest.builder()
                .prompt(buildPrompt())
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

    private String buildPrompt() {
        return "Beautiful prompt"; // TODO: make prompt
    }

}
