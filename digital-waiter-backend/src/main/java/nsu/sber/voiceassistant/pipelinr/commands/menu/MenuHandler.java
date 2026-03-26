package nsu.sber.voiceassistant.pipelinr.commands.menu;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.service.menu.MenuStringBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuHandler implements Command.Handler<MenuCommand, ProcessingResponse> {

    private final MenuStringBuilder menuStringBuilder;

    @Override
    public ProcessingResponse handle(MenuCommand command) {
        String message = menuStringBuilder.formatRelevantMenu(command.getItems());

        log.info("[MenuHandler] Сформирован ответ по меню");

        return ProcessingResponse.builder()
                .success(true)
                .message(message)
                .build();
    }
}