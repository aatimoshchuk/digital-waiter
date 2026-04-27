package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findByLogin(String login);

    Optional<User> findByRestaurantTableId(Integer restaurantTableId);

    boolean existsByLogin(String login);

    User save(User user);

    List<String> findLoginsByPosTerminalGroupIds(List<String> posTerminalGroupIds);

    List<String> findLoginsByPosOrganizationId(String posOrganizationId);

    Optional<String> findLoginByPosTerminalGroupIdAndPosRestaurantTableId(String posTerminalGroupId, String posTableId);

}
