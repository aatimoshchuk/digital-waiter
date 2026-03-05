package nsu.sber.voiceassistant.pipelinr.commands.add;

import an.awesome.pipelinr.Command;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.pipelinr.ResponseParser;
import nsu.sber.voiceassistant.service.menu.MenuStringBuilder;
import nsu.sber.voiceassistant.service.prompt.PromptFactory;
import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.nlu.provider.GigaChatProvider;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
class LlmAddItemsResponse {
    private List<LlmCartItem> items;
}

@Slf4j
@Component
@RequiredArgsConstructor
public class AddItemMiddleware implements Command.Middleware {

    private final GigaChatProvider llmProvider;
    private final MenuStringBuilder menuBuilder;

    private LlmRequest buildRequest(String text) {
        return LlmRequest.builder()
                .prompt(PromptFactory.buildPromptForInvalidAddItem(menuBuilder.buildMenuString()))
                .message(text)
                .build();
    }

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof AddItemCommand addItemCommand)) {
            return next.invoke();
        }

        String userData = addItemCommand.getEntities().stream()
                .map(map -> map.entrySet().stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("; "));

        log.info("[AddItemMiddleware] Входные данные для LLM: {}", userData);

        LlmRequest request = buildRequest(userData);

        LlmResponse response = llmProvider.complete(request);

        log.debug("[AddItemMiddleware] Ответ LLM RAW: {}", response.getContent());

        var llmParsed = ResponseParser.parse(response.getContent(), LlmAddItemsResponse.class);

        if (llmParsed == null || llmParsed.getItems() == null || llmParsed.getItems().isEmpty()) {

            log.warn("[AddItemMiddleware] LLM не вернул корректные данные");

            return (R) ProcessingResponse.builder()
                    .success(false)
                    .message("Не удалось распознать ни одного блюда. Уточните ваш запрос.")
                    .build();
        }

        List<ModifyCartItemRequestDto> result = new ArrayList<>();

        for (LlmCartItem item : llmParsed.getItems()) {

            if (item.getItemId() == null) {

                log.warn("[AddItemMiddleware] Блюдо не найдено в меню: {}", item);

                return (R) ProcessingResponse.builder()
                        .success(false)
                        .message("Некоторые блюда не найдены в меню.")
                        .build();
            }

            int quantity = item.getQuantity() == null || item.getQuantity() <= 0
                    ? 1
                    : item.getQuantity();

            for (int i = 0; i < quantity; i++) {

                ModifyCartItemRequestDto dto = new ModifyCartItemRequestDto();

                dto.setItemId(item.getItemId());
                dto.setSizeId(item.getSizeId());

                result.add(dto);
            }
        }

        addItemCommand.setModifyRequest(result);

        log.info("[AddItemMiddleware] LLM успешно распознал блюда: {}", result);

        return next.invoke();
    }
}