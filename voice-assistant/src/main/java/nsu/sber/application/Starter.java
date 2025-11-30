package nsu.sber.application;

import nsu.sber.model.CommandIntent;
import nsu.sber.service.parser.CommandParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Starter {
    @Autowired
    private CommandIntentProcessor commandIntentProcessor;
    private final CommandParser cmdParser = new CommandParser();

    //TODO: Переименноватьи добавтить куда надо.
    // В функцию передаем запрос юзера, получаем список комманд и вызываем нужные
    public void f(String userResponse) {
        List<CommandIntent> commands = cmdParser.parse(userResponse);
        for (CommandIntent cmd : commands) {
            commandIntentProcessor.process(cmd);
        }

    }
}