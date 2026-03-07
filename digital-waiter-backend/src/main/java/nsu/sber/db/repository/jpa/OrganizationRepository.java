package nsu.sber.db.repository.jpa;

import nsu.sber.db.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer> {

    Optional<OrganizationEntity> findById(int id);

    boolean existsByPosOrganizationId(String posOrganizationId);

    @Query("""
            SELECT o FROM OrganizationEntity o
                JOIN TerminalGroupEntity tg ON tg.organizationId = o.id
                JOIN RestaurantTableEntity rt ON rt.terminalGroupId = tg.id
            WHERE rt.id = :restaurantTableId
            """)
    Optional<OrganizationEntity> findByRestaurantTableId(Integer restaurantTableId);

    @Query("""
            SELECT o FROM OrganizationEntity o
                JOIN TerminalGroupEntity tg ON tg.organizationId = o.id
            WHERE tg.id = :terminalGroupId
            """)
    Optional<OrganizationEntity> findByTerminalGroupId(Integer terminalGroupId);

}
