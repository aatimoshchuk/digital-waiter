package nsu.sber.domain.port.repository.redis;

import nsu.sber.domain.model.menu.Menu;

import java.util.Optional;

public interface MenuRepositoryPort {
    void save(int terminalGroupId, Menu menu);

    Optional<Menu> findByTerminalGroupId(int terminalGroupId);

    void delete(int terminalGroupId);
}
