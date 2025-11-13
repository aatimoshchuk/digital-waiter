package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.Organization;

import java.util.Optional;

public interface OrganizationRepositoryPort {

    Optional<Organization> findById(int id);

}
