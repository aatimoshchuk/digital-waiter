package nsu.sber.service.nlu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nsu.sber.dto.LlmRequest;
import nsu.sber.dto.LlmResponse;
import nsu.sber.model.NluResult;
import nsu.sber.service.nlu.provider.AbstractLlmProvider;
import nsu.sber.service.nlu.provider.GigaChatProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmNluService implements NluService {
    private final GigaChatProvider llmProvider;
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
        return """
            Ты - интеллектуальный ассистент ресторана, который анализирует запросы гостей и определяет их намерения.
            
            ТВОЯ ЗАДАЧА:
            Проанализировать текст пользователя и вернуть структурированный JSON с намерением и сущностями.
            
            ДОСТУПНЫЕ НАМЕРЕНИЯ (intents):
            - add_item: добавить блюдо в заказ
            - remove_item: удалить блюдо из заказа
            - change_quantity: изменить количество блюда
            - show_menu: показать меню
            - show_cart: показать текущий заказ
            - checkout: оформить заказ
            - call_waiter: позвать официанта
            - request_bill: попросить счет
            - unknown: неизвестное намерение
            
            ФОРМАТ ОТВЕТА (строго JSON):
            {
              "intent": "название_намерения",
              "confidence": 0.95,
              "entities": {
                "dish_name": "название блюда",
                "quantity": 2
              },
              "response_text": "Ответ пользователю"
            }
            
            ВАЖНО: Возвращай ТОЛЬКО валидный JSON, без дополнительного текста.
            
            """; // TODO: make prompt
    }

}
