package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.UserEntity;
import nsu.sber.db.mapper.UserEntityMapper;
import nsu.sber.db.repository.jpa.UserRepository;
import nsu.sber.domain.model.entity.User;
import nsu.sber.domain.port.repository.jpa.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Optional<User> findByRestaurantTableId(Integer restaurantTableId) {
        return userRepository.findByRestaurantTableId(restaurantTableId)
                .map(userEntityMapper::entityToUser);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public User save(User user) {
        UserEntity userToSave = userEntityMapper.userToEntity(user);
        return userEntityMapper.entityToUser(userRepository.save(userToSave));
    }

    @Override
    public List<String> findLoginsByPosTerminalGroupIds(List<String> posTerminalGroupIds) {
        return userRepository.findLoginsByPosTerminalGroupIds(posTerminalGroupIds);
    }

    @Override
    public List<String> findLoginsByPosOrganizationId(String posOrganizationId) {
        return userRepository.findLoginsByPosOrganizationId(posOrganizationId);
    }

    @Override
    public Optional<String> findLoginByPosTerminalGroupIdAndPosRestaurantTableId(
            String posTerminalGroupId,
            String posTableId
    ) {
        return userRepository.findLoginByPosTerminalGroupIdAndPosRestaurantTableId(posTerminalGroupId, posTableId);
    }
}
