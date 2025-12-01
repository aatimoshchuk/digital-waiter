package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.organization.CreateOrganizationRequest;
import nsu.sber.domain.model.organization.UpdateOrganizationRequest;
import nsu.sber.domain.port.crypto.ApiKeyCryptoPort;
import nsu.sber.domain.port.repository.jpa.OrganizationRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepositoryPort organizationRepository;
    private final ApiKeyCryptoPort apiKeyCryptoPort;

    private final UserService userService;
    private final TerminalGroupService terminalGroupService;

    public Organization getOrganizationById(int id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.OrganizationWithThisIdNotFoundException(id));
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
    public void deleteOrganizationById(int id) {
        Organization organization = getOrganizationById(id);

        if (!terminalGroupService.findByOrganizationId(organization.getId()).isEmpty()) {
            throw new DigitalWaiterException.OrganizationHasDependenciesException();
        }

        organizationRepository.deleteById(id);

    }

    @Transactional(transactionManager = "transactionManager")
    public Organization updateOrganizationById(int id, UpdateOrganizationRequest updateOrganizationRequest) {
        Organization organization = getOrganizationById(id);

        if (updateOrganizationRequest.getName() != null) {
            organization.setName(updateOrganizationRequest.getName());
        }

        if (updateOrganizationRequest.getPosOrganizationId() != null) {
            String posOrganizationId = updateOrganizationRequest.getPosOrganizationId();

            if (!organization.getPosOrganizationId().equals(posOrganizationId) &&
                    organizationRepository.existsByPosOrganizationId(posOrganizationId)) {
                throw new DigitalWaiterException.OrganizationAlreadyExistException();
            }

            organization.setPosOrganizationId(posOrganizationId);
        }

        if (updateOrganizationRequest.getApiKey() != null) {
            organization.setApiKeyEncrypted(apiKeyCryptoPort.encrypt(updateOrganizationRequest.getApiKey()));
        }

        return organizationRepository.save(organization);
    }
}
