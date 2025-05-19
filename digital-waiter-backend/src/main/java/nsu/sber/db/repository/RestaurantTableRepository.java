package nsu.sber.db.repository;

import nsu.sber.db.entity.RestaurantTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTableEntity, Integer> {

    RestaurantTableEntity findById(int id);

}
