package nsu.sber.voiceassistant.pipelinr.commands.remove;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.dto.LlmRequest;
import nsu.sber.voiceassistant.dto.LlmResponse;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.pipelinr.MenuStringBuilder;
import nsu.sber.voiceassistant.pipelinr.ResponseParser;
import nsu.sber.voiceassistant.service.nlu.provider.GigaChatProvider;
import nsu.sber.voiceassistant.service.prompt.PromptFactory;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveItemMiddleware implements Command.Middleware {

    private final GigaChatProvider llmProvider;
    private final MenuStringBuilder menuBuilder;

    private LlmRequest buildRequest(String text) {
        return LlmRequest.builder()
                .prompt(PromptFactory.BuildPromptForInvalidRemoveItem(menuBuilder.buildMenuString()))
                .message(text)
                .build();
    }

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof RemoveItemCommand removeItemCommand)) {
            return next.invoke();
        }

        log.info("RemoveItem user text: {}", removeItemCommand.getUserText());
        LlmRequest request = buildRequest(removeItemCommand.getUserText());
        LlmResponse response = llmProvider.complete(request);

        log.info("LLM RemoveItem result: {}", response.getContent());

        ModifyCartItemRequestDto dto = ResponseParser.parse(response.getContent(), ModifyCartItemRequestDto.class);

        if (dto == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText("Не могу распознать ваш запрос. Уточните блюдо и размер.")
                    .build();
        }

        if (dto.getItemId() == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText("Не могу распознать название блюда.")
                    .build();
        }


        removeItemCommand.setModifyRequest(dto);

        return next.invoke();
    }
}
