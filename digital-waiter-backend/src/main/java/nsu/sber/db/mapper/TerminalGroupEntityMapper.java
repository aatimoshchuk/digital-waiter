package nsu.sber.db.mapper;

import nsu.sber.db.entity.TerminalGroupEntity;
import nsu.sber.domain.model.entity.TerminalGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TerminalGroupEntityMapper {

    TerminalGroup entityToTerminalGroup(TerminalGroupEntity terminalGroupEntity);

    TerminalGroupEntity terminalGroupToEntity(TerminalGroup terminalGroup);

    List<TerminalGroup> entitiesToTerminalGroups(List<TerminalGroupEntity> terminalGroupEntities);
}
