package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.TerminalGroupEntity;
import nsu.sber.db.mapper.TerminalGroupEntityMapper;
import nsu.sber.db.repository.TerminalGroupRepository;
import nsu.sber.domain.model.TerminalGroup;
import nsu.sber.domain.port.TerminalGroupRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TerminalGroupRepositoryService implements TerminalGroupRepositoryPort {

    private final TerminalGroupRepository terminalGroupRepository;
    private final TerminalGroupEntityMapper terminalGroupEntityMapper;

    @Override
    public TerminalGroup findById(int id) {
        TerminalGroupEntity terminalGroupEntity = terminalGroupRepository.findById(id);
        return terminalGroupEntityMapper.entityToTerminalGroup(terminalGroupEntity);
    }
}
