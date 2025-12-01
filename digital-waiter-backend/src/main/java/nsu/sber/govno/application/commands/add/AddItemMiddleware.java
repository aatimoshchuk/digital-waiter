package nsu.sber.govno.application.commands.add;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import nsu.sber.govno.application.ResponseParser;
import nsu.sber.govno.application.nlp.MenuStringBuilder;
import nsu.sber.govno.application.nlp.prompt.PromptFactory;
import nsu.sber.govno.dto.LlmRequest;
import nsu.sber.govno.dto.LlmResponse;
import nsu.sber.govno.dto.ProcessingResponse;
import nsu.sber.govno.service.nlu.provider.GigaChatProvider;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddItemMiddleware implements Command.Middleware {
    private final GigaChatProvider llmProvider;
    private final MenuStringBuilder menuBuilder;

    private LlmRequest buildRequest(String text) {
        return LlmRequest.builder()
                .prompt(PromptFactory.BuildPromptForInvalidAddItem(menuBuilder.buildMenuString()))
                .message(text)
                .build();
    }

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof AddItemCommand addItemCommand)) {
            return next.invoke();
        }

        String result = addItemCommand.getEntities().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        LlmRequest request = buildRequest(result);

        LlmResponse response = llmProvider.complete(request);
        ModifyCartItemRequestDto dto = ResponseParser.parse(response.getContent(), ModifyCartItemRequestDto.class);
        if (dto == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText("Не могу распознать ваш запрос. Пожалуйста, уточните названия блюда и размер.")
                    .build();
        }

        if (dto.getSizeId() == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText("Не могу распознать ваш запрос. Пожалуйста, уточните названия размер.")
                    .build();
        }

        if (dto.getItemId() == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText("Не могу распознать ваш запрос. Пожалуйста, уточните названия блюда.")
                    .build();
        }

        addItemCommand.setModifyRequest(dto);

        return next.invoke();
    }
}
