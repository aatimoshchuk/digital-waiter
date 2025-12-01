package nsu.sber.govno.application.commands;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Generic;

public interface CommandValidator<C extends Command<R>, R> {
    void validate(C command);

    default boolean matches(C command) {
        Generic<C> commandType = new Generic<C>(getClass()) {};
        return commandType.resolve().isAssignableFrom(command.getClass());
    }
}
