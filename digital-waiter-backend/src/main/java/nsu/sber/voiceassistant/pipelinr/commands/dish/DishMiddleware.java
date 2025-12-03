package nsu.sber.voiceassistant.pipelinr.commands.dish;

import an.awesome.pipelinr.Command;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.pipelinr.MenuStringBuilder;
import nsu.sber.voiceassistant.pipelinr.ResponseParser;
import nsu.sber.voiceassistant.pipelinr.commands.add.AddItemCommand;
import nsu.sber.voiceassistant.service.nlu.provider.GigaChatProvider;
import nsu.sber.voiceassistant.service.prompt.PromptFactory;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
class ModifyCartItemRequestDtoCustom {
    private String itemId;
}

@Data
class LlmItemsResponse {
    private List<ModifyCartItemRequestDtoCustom> items;
}

@Slf4j
@Component
@RequiredArgsConstructor
public class DishMiddleware implements Command.Middleware {

    private final GigaChatProvider llmProvider;
    private final MenuStringBuilder menuBuilder;

    private LlmRequest buildRequest(String dishName) {
        return LlmRequest.builder()
                .prompt(PromptFactory.buildPromptForGetDishId(menuBuilder.buildMenuString(), dishName))
                .message(dishName)
                .build();
    }

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof DishCommand dishCommand)) {
            return next.invoke();
        }

        String query = dishCommand.getEntities().stream()
                .map(map -> map.entrySet().stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining(", "))
                ).collect(Collectors.joining("; "));

        log.info("[DishMiddleware] Запрос к LLM: {}", query);

        LlmRequest request = buildRequest(query);
        log.debug("[DishMiddleware] Prompt: {}", request.getPrompt());

        LlmResponse response = llmProvider.complete(request);
        log.debug("[DishMiddleware] Ответ LLM RAW: {}", response.getContent());

        LlmItemsResponse parsed = ResponseParser.parse(response.getContent(), LlmItemsResponse.class);

        if (parsed == null || parsed.getItems() == null) {
            log.warn("[DishMiddleware] LLM не смог распознать блюдо");
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText("Не могу распознать ваш запрос. Пожалуйста, уточните название блюда.")
                    .build();
        }

        for (var item : parsed.getItems()) {
            if (item.getItemId() == null) {
                log.warn("[DishMiddleware] Блюдо не найдено в меню: {}", item);
                return (R) ProcessingResponse.builder()
                        .success(false)
                        .transcribedText("Некоторые блюда не найдены в меню.")
                        .build();
            }
        }

        dishCommand.setItems(parsed.getItems());
        log.info("[DishMiddleware] Распознано блюд: {}", parsed.getItems().size());

        return next.invoke();
    }
}

