package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.TerminalGroup;

import java.util.Optional;

public interface TerminalGroupRepositoryPort {

    Optional<TerminalGroup> findById(int id);

}
