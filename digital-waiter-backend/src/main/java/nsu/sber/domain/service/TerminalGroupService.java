package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.port.repository.jpa.TerminalGroupRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TerminalGroupService {

    private final TerminalGroupRepositoryPort terminalGroupRepository;

    public TerminalGroup getTerminalGroupById(int id) {
        return terminalGroupRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.TerminalGroupNotFoundException(id));
    }

}
