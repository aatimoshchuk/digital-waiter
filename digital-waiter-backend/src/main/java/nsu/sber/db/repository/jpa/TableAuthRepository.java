package nsu.sber.db.repository.jpa;

import nsu.sber.db.entity.TableAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableAuthRepository extends JpaRepository<TableAuthEntity, Integer> {

    Optional<TableAuthEntity> findByLogin(String login);

    Optional<TableAuthEntity> findById(int id);

}
