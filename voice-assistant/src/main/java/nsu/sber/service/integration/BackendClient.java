package nsu.sber.service.integration;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import nsu.sber.dto.CommandSet;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.model.CommandIntent;
import nsu.sber.service.commands.AddItemCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BackendClient {
    private final Pipeline pipeline;

    public ProcessingResponse execute(List<CommandIntent> commands, String sessionId) {
        for (CommandIntent command : commands) {
            execute(command);
        }
        return new ProcessingResponse();
    }

    private void execute(CommandIntent commandIntent) {
        //TODO Дописать switch!
        switch (commandIntent.getType()) {
            case ADD_ITEM:

                Object addItemDto = commandIntent.getParameters().get("item");
                new AddItemCommand(addItemDto).execute(pipeline);
                break;

            default:
                throw new IllegalArgumentException("Unknown intent: " + commandIntent.getType());
        }
    }
}
