package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.TerminalGroup;
import nsu.sber.domain.port.TerminalGroupRepositoryPort;
import nsu.sber.exception.ErrorType;
import nsu.sber.exception.ServiceException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TerminalGroupService {

    private final TerminalGroupRepositoryPort terminalGroupRepository;

    public TerminalGroup getTerminalGroupById(int id) {
        TerminalGroup terminalGroup = terminalGroupRepository.findById(id);

        if (terminalGroup == null) {
            String errorMessage = "Терминальная группа с id %d не найдена".formatted(id);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.TERMINAL_GROUP_NOT_FOUND);
        }

        return terminalGroup;
    }

}
