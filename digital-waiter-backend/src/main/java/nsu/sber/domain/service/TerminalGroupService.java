package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.terminal_group.CreateTerminalGroupRequest;
import nsu.sber.domain.model.terminal_group.UpdateTerminalGroupRequest;
import nsu.sber.domain.port.repository.jpa.RestaurantTableRepositoryPort;
import nsu.sber.domain.port.repository.jpa.TerminalGroupRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TerminalGroupService {
    private final TerminalGroupRepositoryPort terminalGroupRepository;
    private final RestaurantTableRepositoryPort restaurantTableRepository;

    private final UserService userService;
    private final OrganizationService organizationService;

    public TerminalGroup getTerminalGroup(Integer id) {
        return terminalGroupRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.TerminalGroupWithThisIdNotFoundException(id));
    }

    public List<TerminalGroup> getTerminalGroupsByOrganizationId(Integer organizationId) {
        if (!organizationService.isOrganizationExists(organizationId)) {
            throw new DigitalWaiterException.OrganizationWithThisIdNotFoundException(organizationId);
        }

        return terminalGroupRepository.findByOrganizationId(organizationId);
    }

    public boolean isTerminalGroupExists(Integer id) {
        return terminalGroupRepository.existsById(id);
    }

    public TerminalGroup getCurrentTerminalGroup() {
        return terminalGroupRepository.findByRestaurantTableId(userService.getCurrentUser().getRestaurantTableId())
                .orElseThrow(DigitalWaiterException.NoTerminalGroupForRestaurantTableException::new);
    }

    public void createTerminalGroup(CreateTerminalGroupRequest createTerminalGroupRequest) {
        Integer organizationId = createTerminalGroupRequest.getOrganizationId();

        if (!organizationService.isOrganizationExists(organizationId)) {
            throw new DigitalWaiterException.OrganizationWithThisIdNotFoundException(organizationId);
        }

        if (terminalGroupRepository.existsByPosTerminalGroupIdAndOrganizationId(
                createTerminalGroupRequest.getPosTerminalGroupId(),
                organizationId
        )) {
            throw new DigitalWaiterException.TerminalGroupAlreadyExistException();
        }

        TerminalGroup terminalGroup = TerminalGroup
                .builder()
                .name(createTerminalGroupRequest.getName())
                .posTerminalGroupId(createTerminalGroupRequest.getPosTerminalGroupId())
                .posExternalMenuId(createTerminalGroupRequest.getPosExternalMenuId())
                .organizationId(organizationId)
                .build();

        terminalGroupRepository.save(terminalGroup);
    }

    public List<TerminalGroup> getTerminalGroups(Integer organizationId) {
        if (organizationId != null) {
            return getTerminalGroupsByOrganizationId(organizationId);
        }

        return terminalGroupRepository.findAll();
    }

    @Transactional(transactionManager = "transactionManager")
    public void deleteTerminalGroup(Integer id) {
        TerminalGroup terminalGroup = getTerminalGroup(id);

        if (restaurantTableRepository.existsByTerminalGroupId(id)) {
            throw new DigitalWaiterException.TerminalGroupHasDependenciesException();
        }

        terminalGroupRepository.delete(terminalGroup);
    }

    @Transactional(transactionManager = "transactionManager")
    public TerminalGroup updateTerminalGroup(Integer id, UpdateTerminalGroupRequest request) {
        TerminalGroup terminalGroup = getTerminalGroup(id);

        if (request.getOrganizationId() != null &&
                !organizationService.isOrganizationExists(request.getOrganizationId())) {
            throw new DigitalWaiterException.OrganizationWithThisIdNotFoundException(request.getOrganizationId());
        }

        Integer targetOrganizationId = request.getOrganizationId() != null
                ? request.getOrganizationId()
                : terminalGroup.getOrganizationId();

        String targetPosTerminalGroupId = request.getPosTerminalGroupId() != null
                ? request.getPosTerminalGroupId()
                : terminalGroup.getPosTerminalGroupId();

        boolean organizationChanged = !Objects.equals(targetOrganizationId, terminalGroup.getOrganizationId());
        boolean posGroupIdChanged = !Objects.equals(targetPosTerminalGroupId, terminalGroup.getPosTerminalGroupId());

        if ((organizationChanged || posGroupIdChanged) &&
                terminalGroupRepository.existsByPosTerminalGroupIdAndOrganizationId(
                    targetPosTerminalGroupId,
                    targetOrganizationId
            )) {
                throw new DigitalWaiterException.TerminalGroupAlreadyExistException();
        }

        if (request.getName() != null) {
            terminalGroup.setName(request.getName());
        }
        if (request.getPosExternalMenuId() != null) {
            terminalGroup.setPosExternalMenuId(request.getPosExternalMenuId());
        }

        terminalGroup.setOrganizationId(targetOrganizationId);
        terminalGroup.setPosTerminalGroupId(targetPosTerminalGroupId);

        return terminalGroupRepository.save(terminalGroup);
    }

}
