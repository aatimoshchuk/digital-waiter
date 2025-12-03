package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.OrganizationEntity;
import nsu.sber.db.mapper.OrganizationEntityMapper;
import nsu.sber.db.repository.jpa.OrganizationRepository;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.port.repository.jpa.OrganizationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationRepositoryService implements OrganizationRepositoryPort {

    private final OrganizationRepository organizationRepository;
    private final OrganizationEntityMapper organizationEntityMapper;

    @Override
    public Optional<Organization> findById(Integer id) {
        return organizationRepository.findById(id)
                .map(organizationEntityMapper::entityToOrganization);
    }

    @Override
    public boolean existsById(Integer id) {
        return organizationRepository.existsById(id);
    }

    @Override
    public boolean existsByPosOrganizationId(String posOrganizationId) {
        return organizationRepository.existsByPosOrganizationId(posOrganizationId);
    }

    @Override
    public Organization save(Organization organization) {
        OrganizationEntity organizationToSave = organizationEntityMapper.organizationToEntity(organization);
        return organizationEntityMapper.entityToOrganization(organizationRepository.save(organizationToSave));
    }

    @Override
    public List<Organization> findAll() {
        return organizationEntityMapper.entitiesToOrganizations(organizationRepository.findAll());
    }

    @Override
    public void delete(Organization organization) {
        organizationRepository.delete(organizationEntityMapper.organizationToEntity(organization));
    }

    @Override
    public Optional<Organization> findByRestaurantTableId(Integer restaurantTableId) {
        return organizationRepository.findByRestaurantTableId(restaurantTableId)
                .map(organizationEntityMapper::entityToOrganization);
    }
}
