package nsu.sber.voiceassistant.service.prompt;

import nsu.sber.voiceassistant.pipelinr.commands.add.AddItemCommand;
import nsu.sber.voiceassistant.pipelinr.commands.advice.AdviceCommand;
import nsu.sber.voiceassistant.pipelinr.commands.change.ChangeQuantityCommand;
import nsu.sber.voiceassistant.pipelinr.commands.clear.ClearCartCommand;
import nsu.sber.voiceassistant.pipelinr.commands.dish.DishCommand;
import nsu.sber.voiceassistant.pipelinr.commands.menu.MenuCommand;
import nsu.sber.voiceassistant.pipelinr.commands.remove.RemoveItemCommand;

import java.util.List;

public final class PromptCommandRegistry {
    private PromptCommandRegistry() {}

    public static final List<Class<?>> COMMANDS = List.of(
            AddItemCommand.class,
            RemoveItemCommand.class,
            MenuCommand.class,
            DishCommand.class,
            AdviceCommand.class,
            ChangeQuantityCommand.class,
            ClearCartCommand.class
    );
}