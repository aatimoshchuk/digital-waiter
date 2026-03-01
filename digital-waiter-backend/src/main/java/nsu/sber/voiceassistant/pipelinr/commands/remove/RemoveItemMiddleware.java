package nsu.sber.voiceassistant.pipelinr.commands.remove;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.menu.MenuStringBuilder;
import nsu.sber.voiceassistant.pipelinr.ResponseParser;
import nsu.sber.voiceassistant.service.nlu.provider.GigaChatProvider;
import nsu.sber.voiceassistant.service.prompt.PromptFactory;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveItemMiddleware implements Command.Middleware {

    private final GigaChatProvider llmProvider;
    private final MenuStringBuilder menuBuilder;

    private LlmRequest buildRequest(String text) {
        return LlmRequest.builder()
                .prompt(PromptFactory.buildPromptForInvalidRemoveItem(menuBuilder.buildMenuString()))
                .message(text)
                .build();
    }


    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof RemoveItemCommand removeItemCommand)) {
            return next.invoke();
        }

        String userData;
        if (removeItemCommand.getEntities() != null && !removeItemCommand.getEntities().isEmpty()) {
            userData = removeItemCommand.getEntities().stream()
                    .map(map -> map.entrySet().stream()
                            .map(e -> e.getKey() + ": " + e.getValue())
                            .collect(Collectors.joining(", ")))
                    .collect(Collectors.joining("; "));
        } else {
            userData = removeItemCommand.getText();
        }

        log.info("RemoveItem input: {}", userData);

        LlmRequest request = buildRequest(userData);
        LlmResponse response = llmProvider.complete(request);

        log.info("LLM RemoveItem result: {}", response.getContent());

        ModifyCartItemRequestDto dto = ResponseParser.parse(response.getContent(), ModifyCartItemRequestDto.class);

        if (dto == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .message("Не могу распознать ваш запрос. Уточните блюдо и размер.")
                    .build();
        }

        if (dto.getItemId() == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .message("Не могу распознать название блюда.")
                    .build();
        }

        removeItemCommand.setModifyRequest(dto);
        return next.invoke();
    }
}
