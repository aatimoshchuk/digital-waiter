package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.mapper.TerminalGroupEntityMapper;
import nsu.sber.db.repository.jpa.TerminalGroupRepository;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.port.repository.jpa.TerminalGroupRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TerminalGroupRepositoryService implements TerminalGroupRepositoryPort {

    private final TerminalGroupRepository terminalGroupRepository;
    private final TerminalGroupEntityMapper terminalGroupEntityMapper;

    @Override
    public Optional<TerminalGroup> findById(int id) {
        return terminalGroupRepository.findById(id)
                .map(terminalGroupEntityMapper::entityToTerminalGroup);
    }

    @Override
    public Optional<TerminalGroup> findByRestaurantTableId(Integer restaurantTableId) {
        return terminalGroupRepository.findByRestaurantTableId(restaurantTableId)
                .map(terminalGroupEntityMapper::entityToTerminalGroup);
    }

    @Override
    public List<TerminalGroup> findByOrganizationId(Integer organizationId) {
        return terminalGroupEntityMapper.entitiesToTerminalGroups(
                terminalGroupRepository.findByOrganizationId(organizationId)
        );
    }
}
