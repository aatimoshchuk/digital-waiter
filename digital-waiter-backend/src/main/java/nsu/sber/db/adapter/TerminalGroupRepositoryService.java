package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.TerminalGroupEntity;
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
    public boolean existsById(Integer id) {
        return terminalGroupRepository.existsById(id);
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

    @Override
    public boolean existsByOrganizationId(Integer organizationId) {
        return terminalGroupRepository.existsByOrganizationId(organizationId);
    }

    @Override
    public boolean existsByPosTerminalGroupIdAndOrganizationId(String posTerminalGroupId, Integer organizationId) {
        return terminalGroupRepository.existsByPosTerminalGroupIdAndOrganizationId(
                posTerminalGroupId,
                organizationId
        );
    }

    @Override
    public TerminalGroup save(TerminalGroup terminalGroup) {
        TerminalGroupEntity terminalGroupToSave = terminalGroupEntityMapper.terminalGroupToEntity(terminalGroup);
        return terminalGroupEntityMapper.entityToTerminalGroup(terminalGroupRepository.save(terminalGroupToSave));
    }

    @Override
    public List<TerminalGroup> findAll() {
        return terminalGroupEntityMapper.entitiesToTerminalGroups(terminalGroupRepository.findAll());
    }

    @Override
    public void delete(TerminalGroup terminalGroup) {
        terminalGroupRepository.delete(terminalGroupEntityMapper.terminalGroupToEntity(terminalGroup));
    }
}
