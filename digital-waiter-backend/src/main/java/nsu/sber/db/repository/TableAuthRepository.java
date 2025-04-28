package nsu.sber.db.repository;

import nsu.sber.db.entity.TableAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableAuthRepository extends JpaRepository<TableAuthEntity, Integer> {

    TableAuthEntity findByLogin(String login);

    TableAuthEntity findById(int id);

}
