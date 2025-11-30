package nsu.sber.application.commands.dish;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.application.nlp.MenuStringBuilder;
import nsu.sber.application.nlp.prompt.PromptFactory;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.application.nlp.gigachat.Gigachat;
import nsu.sber.service.parser.ResponseParser;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DishMiddleware implements Command.Middleware {

    private final Gigachat aiClient;
    private final MenuStringBuilder menuBuilder;

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {

        if (!(command instanceof DishCommand dishCommand)) {
            return next.invoke();
        }

        String prompt = PromptFactory.buildPromptForDishRecognition(
                menuBuilder.buildMenuString(),
                dishCommand.getDishId()
        );

        String aiResponse = aiClient.sendPrompt(prompt);
        var result = ResponseParser.parse(aiResponse, ModifyCartItemRequestDto.class);

        if (result == null || result.getItemId() == null) {
            return (R) ProcessingResponse.builder()
                    .success(false)
                    .transcribedText(
                            "Не могу найти указанное блюдо. Пожалуйста, уточните название."
                    )
                    .build();
        }

        dishCommand.setDishId(result.getItemId());

        return next.invoke();
    }
}
