package nsu.sber.db.repository.jpa;

import nsu.sber.db.entity.TerminalGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerminalGroupRepository extends JpaRepository<TerminalGroupEntity, Integer> {

    Optional<TerminalGroupEntity> findById(int id);

    @Query("""
            SELECT tg FROM TerminalGroupEntity tg
                JOIN RestaurantTableEntity rt ON rt.terminalGroupId = tg.id
            WHERE rt.id = :restaurantTableId
            """)
    Optional<TerminalGroupEntity> findByRestaurantTableId(Integer restaurantTableId);

    List<TerminalGroupEntity> findByOrganizationId(Integer organizationId);

    boolean existsByOrganizationId(Integer organizationId);

    boolean existsByPosTerminalGroupIdAndOrganizationId(String postTerminalGroupId, Integer organizationId);

}
