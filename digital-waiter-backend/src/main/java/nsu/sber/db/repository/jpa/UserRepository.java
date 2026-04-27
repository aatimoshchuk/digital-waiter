package nsu.sber.db.repository.jpa;

import nsu.sber.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByRestaurantTableId(Integer restaurantTableId);

    boolean existsByLogin(String login);

    @Query("""
        SELECT DISTINCT u.login
        FROM UserEntity u
        JOIN RestaurantTableEntity rt ON rt.id = u.restaurantTableId
        JOIN TerminalGroupEntity tg ON tg.id = rt.terminalGroupId
        WHERE tg.posTerminalGroupId IN :posTerminalGroupIds
    """)
    List<String> findLoginsByPosTerminalGroupIds(@Param("posTerminalGroupIds") List<String> posTerminalGroupIds);

    @Query("""
        SELECT DISTINCT u.login
        FROM UserEntity u
        JOIN RestaurantTableEntity rt ON rt.id = u.restaurantTableId
        JOIN TerminalGroupEntity tg ON tg.id = rt.terminalGroupId
        WHERE tg.posTerminalGroupId = :posTerminalGroupId AND rt.posTableId = :posTableId

    """)
    Optional<String> findLoginByPosTerminalGroupIdAndPosRestaurantTableId(
            @Param("posTerminalGroupId") String posTerminalGroupId,
            @Param("posTableId") String posTableId
    );
}
