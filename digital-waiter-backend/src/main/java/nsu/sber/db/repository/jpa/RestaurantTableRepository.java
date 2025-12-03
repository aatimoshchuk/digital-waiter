package nsu.sber.db.repository.jpa;

import nsu.sber.db.entity.RestaurantTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTableEntity, Integer> {

    Optional<RestaurantTableEntity> findById(int id);

    List<RestaurantTableEntity> findByTerminalGroupId(Integer terminalGroupId);

    boolean existsByTerminalGroupId(Integer terminalGroupId);

}
