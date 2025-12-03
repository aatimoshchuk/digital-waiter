package nsu.sber.voiceassistant.pipelinr.commands.advice;

import an.awesome.pipelinr.Command;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import nsu.sber.voiceassistant.dto.ProcessingResponse;

import nsu.sber.voiceassistant.pipelinr.MenuStringBuilder;
import nsu.sber.voiceassistant.pipelinr.ResponseParser;
import nsu.sber.voiceassistant.service.nlu.provider.GigaChatProvider;
import nsu.sber.voiceassistant.service.prompt.PromptFactory;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Data
class LlmAdviceResponse {
    private String advice;
}

@Slf4j
@Component
@RequiredArgsConstructor
public class AdviceMiddleware implements Command.Middleware {

    private final GigaChatProvider llmProvider;
    private final MenuStringBuilder menuBuilder;

    private LlmRequest buildRequest(String context) {
        return LlmRequest.builder()
                .prompt(PromptFactory.buildAdvicePrompt(menuBuilder.buildMenuString(), context))
                .message(context == null ? "" : context)
                .build();
    }

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof AdviceCommand adviceCommand)) {
            return next.invoke();
        }

        String contextStr = adviceCommand.getContext() == null
                ? null
                : adviceCommand.getContext().entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));

        log.info("[AdviceMiddleware] Входной контекст: {}", contextStr);

        LlmRequest request = buildRequest(contextStr);
        log.debug("[AdviceMiddleware] Запрос в LLM: {}", request);

        LlmResponse response = llmProvider.complete(request);
        log.debug("[AdviceMiddleware] RAW ответ LLM: {}", response.getContent());

        LlmAdviceResponse advice = ResponseParser.parse(response.getContent(), LlmAdviceResponse.class);

        if (advice == null || advice.getAdvice() == null || advice.getAdvice().isBlank()) {
            log.warn("[AdviceMiddleware] LLM вернул пустой или некорректный ответ");
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText("Не могу сформировать совет. Попробуйте переформулировать запрос.")
                    .build();
        }

        adviceCommand.setContext(Map.of("advice", advice.getAdvice()));
        log.info("[AdviceMiddleware] Успешная рекомендация: {}", advice.getAdvice());

        return next.invoke();
    }
}
