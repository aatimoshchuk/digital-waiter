package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.User;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findByLogin(String login);

}
