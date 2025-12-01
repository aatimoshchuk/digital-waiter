package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.port.repository.jpa.TerminalGroupRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminalGroupService {
    private final TerminalGroupRepositoryPort terminalGroupRepository;

    private final UserService userService;

    public TerminalGroup getTerminalGroupById(int id) {
        return terminalGroupRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.TerminalGroupWithThisIdNotFoundException(id));
    }

    public List<TerminalGroup> findByOrganizationId(int organizationId) {
        return terminalGroupRepository.findByOrganizationId(organizationId);
    }

    public TerminalGroup getCurrentTerminalGroup() {
        return terminalGroupRepository.findByRestaurantTableId(userService.getCurrentUser().getRestaurantTableId())
                .orElseThrow(DigitalWaiterException.NoTerminalGroupForRestaurantTableException::new);
    }

}
