package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.port.repository.jpa.OrganizationRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepositoryPort organizationRepository;

    public Organization getOrganizationById(int id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.OrganizationNotFoundException(id));
    }
}
