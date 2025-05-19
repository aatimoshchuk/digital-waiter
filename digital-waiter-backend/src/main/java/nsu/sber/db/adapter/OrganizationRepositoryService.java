package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.OrganizationEntity;
import nsu.sber.db.mapper.OrganizationEntityMapper;
import nsu.sber.db.repository.OrganizationRepository;
import nsu.sber.domain.model.Organization;
import nsu.sber.domain.port.OrganizationRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationRepositoryService implements OrganizationRepositoryPort {

    private final OrganizationRepository organizationRepository;
    private final OrganizationEntityMapper organizationEntityMapper;

    @Override
    public Organization findById(int id) {
        OrganizationEntity organizationEntity = organizationRepository.findById(id);
        return organizationEntityMapper.entityToOrganization(organizationEntity);
    }
}
