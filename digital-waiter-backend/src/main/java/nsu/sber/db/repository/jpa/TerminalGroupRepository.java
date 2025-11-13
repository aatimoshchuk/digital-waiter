package nsu.sber.db.repository.jpa;

import nsu.sber.db.entity.TerminalGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerminalGroupRepository extends JpaRepository<TerminalGroupEntity, Integer> {

    Optional<TerminalGroupEntity> findById(int id);

}
