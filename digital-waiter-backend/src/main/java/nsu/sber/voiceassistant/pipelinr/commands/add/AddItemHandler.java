package nsu.sber.voiceassistant.pipelinr.commands.add;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.service.CartService;
import nsu.sber.voiceassistant.dto.ProcessingResponse;
import nsu.sber.web.dto.ModifyCartItemRequestDto;
import nsu.sber.web.mapper.CartDtoMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddItemHandler implements Command.Handler<AddItemCommand, ProcessingResponse> {

    private final CartService cartService;
    private final CartDtoMapper cartDtoMapper;

    @Override
    public ProcessingResponse handle(AddItemCommand command) {

        log.info("[AddItemHandler] Начато добавление блюд: {} шт.", command.getModifyRequest().size());

        for (ModifyCartItemRequestDto dto : command.getModifyRequest()) {
            log.debug("[AddItemHandler] Добавление: {}", dto);
            cartService.addItem(cartDtoMapper.dtoToModifyCartItemRequest(dto));
        }

        String msg = command.getModifyRequest().size() > 1
                ? "Блюда добавлены успешно"
                : "Блюдо добавлено успешно";

        log.info("[AddItemHandler] {}", msg);

        return ProcessingResponse.builder()
                .success(true)
                .transcribedText(msg)
                .build();
    }
}
