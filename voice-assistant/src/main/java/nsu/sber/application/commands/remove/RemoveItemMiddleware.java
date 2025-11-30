package nsu.sber.application.commands.remove;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import nsu.sber.application.nlp.MenuStringBuilder;
import nsu.sber.application.nlp.prompt.PromptFactory;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.application.nlp.gigachat.Gigachat;
import nsu.sber.service.parser.ResponseParser;

import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveItemMiddleware implements Command.Middleware {

    private final Gigachat aiClient;
    private final MenuStringBuilder menuBuilder;


    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof RemoveItemCommand removeItemCommand)) {
            return next.invoke();
        }

        String aiResult = aiClient.sendPrompt(
                PromptFactory.BuildPromptForInvalidRemoveItem(menuBuilder.buildMenuString())
        );

        ModifyCartItemRequestDto dto = ResponseParser.parse(aiResult, ModifyCartItemRequestDto.class);

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
