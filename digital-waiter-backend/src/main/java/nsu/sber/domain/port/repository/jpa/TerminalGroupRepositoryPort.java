package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.TerminalGroup;

import java.util.List;
import java.util.Optional;

public interface TerminalGroupRepositoryPort {

    Optional<TerminalGroup> findById(int id);

    boolean existsById(Integer id);

    Optional<TerminalGroup> findByRestaurantTableId(Integer restaurantTableId);

    List<TerminalGroup> findByOrganizationId(Integer organizationId);

    boolean existsByOrganizationId(Integer organizationId);

    boolean existsByPosTerminalGroupIdAndOrganizationId(String posTerminalGroupId, Integer organizationId);

    TerminalGroup save(TerminalGroup terminalGroup);

    List<TerminalGroup> findAll();

    void delete(TerminalGroup terminalGroup);

}
