package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.mapper.OrganizationEntityMapper;
import nsu.sber.db.repository.jpa.OrganizationRepository;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.port.repository.jpa.OrganizationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationRepositoryService implements OrganizationRepositoryPort {

    private final OrganizationRepository organizationRepository;
    private final OrganizationEntityMapper organizationEntityMapper;

    @Override
    public Optional<Organization> findById(int id) {
        return organizationRepository.findById(id)
                .map(organizationEntityMapper::entityToOrganization);
    }
}
