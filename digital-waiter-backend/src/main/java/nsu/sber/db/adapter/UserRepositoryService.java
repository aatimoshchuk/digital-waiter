package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.mapper.UserEntityMapper;
import nsu.sber.db.repository.jpa.UserRepository;
import nsu.sber.domain.model.entity.User;
import nsu.sber.domain.port.repository.jpa.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserRepositoryService implements UserRepositoryPort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userEntityMapper::entityToUser);
    }
}
