package nsu.sber.db.repository;

import nsu.sber.db.entity.TerminalGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalGroupRepository extends JpaRepository<TerminalGroupEntity, Integer> {

    TerminalGroupEntity findById(int id);

}
