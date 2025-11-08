package nsu.sber.service.parser;

import lombok.RequiredArgsConstructor;
import nsu.sber.model.CommandIntent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandParser {
    public CommandIntent parse(String nluResult) {
        // TODO: привести результат к запросу в бэк
        return new CommandIntent();
    }

}
