package nsu.sber.voiceassistant.pipelinr.commands.menu;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.pipelinr.ResponseParser;
import nsu.sber.voiceassistant.service.menu.MenuStringBuilder;
import nsu.sber.voiceassistant.service.nlu.provider.GigaChatProvider;
import nsu.sber.voiceassistant.service.prompt.PromptFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuMiddleware implements Command.Middleware {

    private final GigaChatProvider llmProvider;
    private final MenuStringBuilder menuBuilder;

    private LlmRequest buildRequest(String userText) {
        return LlmRequest.builder()
                .prompt(PromptFactory.buildPromptForMenuSelection(
                        menuBuilder.buildCompactMenuForPrompt(),
                        userText
                ))
                .message(userText)
                .build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {
        if (!(command instanceof MenuCommand menuCommand)) {
            return next.invoke();
        }

        String userText = menuCommand.getText();
        if (userText == null || userText.isBlank()) {
            userText = "покажи меню";
        }

        log.info("[MenuMiddleware] Запрос пользователя: {}", userText);

        LlmResponse response = llmProvider.complete(buildRequest(userText));
        log.debug("[MenuMiddleware] Ответ LLM RAW: {}", response.getContent());

        LlmMenuResponse parsed = ResponseParser.parse(response.getContent(), LlmMenuResponse.class);

        if (parsed == null) {
            log.warn("[MenuMiddleware] LLM вернул невалидный JSON");

            return (R) ProcessingResponse.builder()
                    .success(false)
                    .message("Не удалось подобрать блюда. Попробуйте уточнить запрос.")
                    .build();
        }

        if (parsed.getItems() == null) {
            parsed.setItems(Collections.emptyList());
        }

        menuCommand.setItems(parsed.getItems());

        log.info("[MenuMiddleware] Найдено позиций: {}", parsed.getItems().size());

        return next.invoke();
    }
}