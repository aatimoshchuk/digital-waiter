package nsu.sber.db.repository;

import nsu.sber.db.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer> {

    OrganizationEntity findById(int id);

}
