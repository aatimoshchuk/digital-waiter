package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.repository.jpa.TerminalGroupRepository;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.organization.CreateOrganizationRequest;
import nsu.sber.domain.model.organization.UpdateOrganizationRequest;
import nsu.sber.domain.port.crypto.ApiKeyCryptoPort;
import nsu.sber.domain.port.repository.jpa.OrganizationRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepositoryPort organizationRepository;
    private final TerminalGroupRepository terminalGroupRepository;

    private final ApiKeyCryptoPort apiKeyCryptoPort;

    private final UserService userService;

    public Organization getOrganization(int id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.OrganizationWithThisIdNotFoundException(id));
    }

    public Organization getOrganizationByTerminalGroupId(Integer terminalGroupId) {
        return organizationRepository.findByTerminalGroupId(terminalGroupId)
                .orElseThrow(DigitalWaiterException.NoOrganizationForTerminalGroupException::new);
    }

    public boolean isOrganizationExists(int id) {
        return organizationRepository.existsById(id);
    }

    public Organization getCurrentOrganization() {
        return organizationRepository.findByRestaurantTableId(userService.getCurrentUser().getRestaurantTableId())
                .orElseThrow(DigitalWaiterException.NoOrganizationForRestaurantTableException::new);
    }

    public void createOrganization(CreateOrganizationRequest createOrganizationRequest) {
        if (organizationRepository.existsByPosOrganizationId(createOrganizationRequest.getPosOrganizationId())) {
            throw new DigitalWaiterException.OrganizationAlreadyExistException();
        }

        Organization organization = Organization
                .builder()
                .name(createOrganizationRequest.getName())
                .posOrganizationId(createOrganizationRequest.getPosOrganizationId())
                .apiKeyEncrypted(apiKeyCryptoPort.encrypt(createOrganizationRequest.getApiKey()))
                .build();

        organizationRepository.save(organization);
    }

    public List<Organization> getOrganizations() {
        return organizationRepository.findAll();
    }

    @Transactional(transactionManager = "transactionManager")
    public void deleteOrganization(int id) {
        Organization organization = getOrganization(id);

        if (terminalGroupRepository.existsByOrganizationId(id)) {
            throw new DigitalWaiterException.OrganizationHasDependenciesException();
        }

        organizationRepository.delete(organization);

    }

    @Transactional(transactionManager = "transactionManager")
    public Organization updateOrganization(int id, UpdateOrganizationRequest request) {
        Organization organization = getOrganization(id);

        if (request.getName() != null) {
            organization.setName(request.getName());
        }

        if (request.getPosOrganizationId() != null) {
            String posOrganizationId = request.getPosOrganizationId();

            if (!Objects.equals(organization.getPosOrganizationId(), posOrganizationId) &&
                    organizationRepository.existsByPosOrganizationId(posOrganizationId)) {
                throw new DigitalWaiterException.OrganizationAlreadyExistException();
            }

            organization.setPosOrganizationId(posOrganizationId);
        }

        if (request.getApiKey() != null) {
            organization.setApiKeyEncrypted(apiKeyCryptoPort.encrypt(request.getApiKey()));
        }

        return organizationRepository.save(organization);
    }
}
