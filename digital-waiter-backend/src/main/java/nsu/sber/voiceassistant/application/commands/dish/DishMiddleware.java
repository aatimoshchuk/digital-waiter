//package nsu.sber.govno.application.commands.dish;
//
//import an.awesome.pipelinr.Command;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import nsu.sber.govno.application.ResponseParser;
//import nsu.sber.govno.application.nlp.MenuStringBuilder;
//import nsu.sber.govno.application.nlp.prompt.PromptFactory;
//import nsu.sber.govno.dto.ProcessingResponse;
//import nsu.sber.web.dto.ModifyCartItemRequestDto;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class DishMiddleware implements Command.Middleware {
//
//    //private final Gigachat aiClient;
//    private final MenuStringBuilder menuBuilder;
//
//    @Override
//    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {
//
//        if (!(command instanceof DishCommand dishCommand)) {
//            return next.invoke();
//        }
//
//        String prompt = PromptFactory.buildPromptForDishRecognition(
//                menuBuilder.buildMenuString(),
//                dishCommand.getDishId()
//        );
//
////        String aiResponse = aiClient.sendPrompt(prompt);
//        String aiResponse = "";
//        var result = ResponseParser.parse(aiResponse, ModifyCartItemRequestDto.class);
//
//        if (result == null || result.getItemId() == null) {
//            return (R) ProcessingResponse.builder()
//                    .success(false)
//                    .transcribedText(
//                            "Не могу найти указанное блюдо. Пожалуйста, уточните название."
//                    )
//                    .build();
//        }
//
//        dishCommand.setDishId(result.getItemId());
//
//        return next.invoke();
//    }
//}
