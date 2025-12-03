package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepositoryPort {

    Optional<Organization> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByPosOrganizationId(String posOrganizationId);

    Organization save(Organization organization);

    List<Organization> findAll();

    void delete(Organization organization);

    Optional<Organization> findByRestaurantTableId(Integer restaurantTableId);

}
