package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepositoryPort {

    Optional<Organization> findById(int id);

    boolean existsByPosOrganizationId(String posOrganizationId);

    Organization save(Organization organization);

    List<Organization> findAll();

    void deleteById(int id);

    Optional<Organization> findByRestaurantTableId(Integer restaurantTableId);

}
