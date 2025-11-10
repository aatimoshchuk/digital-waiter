package nsu.sber.service.parser;

import lombok.RequiredArgsConstructor;
import nsu.sber.model.CommandIntent;
import nsu.sber.model.IntentType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandParser {

    public CommandIntent parse(String nluResult) {
        // Пример чет такое: {"intent": "add_item", "parameters": {"item": {...}}}
        //  распарсить джейсон и заполнить поля
        //IntentType type = IntentType.ADD_ITEM; //TODO: Реализовать логику распознавания
        //Map<String, Object> params = Map.of("item", new ModifyCartItemRequestDto(1L, 2));
        //return new CommandIntent(type, params);
        return null;
    }
}
