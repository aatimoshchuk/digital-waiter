package nsu.sber.domain.port;

import nsu.sber.domain.model.TerminalGroup;

public interface TerminalGroupRepositoryPort {

    TerminalGroup findById(int id);

}
