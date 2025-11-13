package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.TableAuth;

import java.util.Optional;

public interface TableAuthRepositoryPort {

    Optional<TableAuth> findByLogin(String login);

    Optional<TableAuth> findById(int id);

}
