package nsu.sber.application.commands.add;

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
public class AddItemMiddleware implements Command.Middleware {
    private final Gigachat aiClient;

    private final MenuStringBuilder menuBuilder;

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof AddItemCommand addItemCommand)) {
            return next.invoke();
        }

        String aiResult = aiClient.sendPrompt(PromptFactory.BuildPromptForInvalidAddItem(menuBuilder.buildMenuString()));

        ModifyCartItemRequestDto dto = ResponseParser.parse(aiResult, ModifyCartItemRequestDto.class);
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
