package nsu.sber.voiceassistant.pipelinr.commands.menu;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.voiceassistant.pipelinr.MenuStringBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuHandler implements Command.Handler<MenuCommand, ProcessingResponse> {
    private final MenuStringBuilder menuBuilder;

    @Override
    public ProcessingResponse handle(MenuCommand command) {
        var menu = menuBuilder.buildMenuItemsString();
        return ProcessingResponse.builder()
                .success(true)
                .transcribedText("Наше меню " + menu)
                .build();
    }

}
