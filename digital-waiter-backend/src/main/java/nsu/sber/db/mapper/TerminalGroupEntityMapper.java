package nsu.sber.db.mapper;

import nsu.sber.db.entity.TerminalGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TerminalGroupEntityMapper {

    nsu.sber.domain.model.entity.TerminalGroup entityToTerminalGroup(TerminalGroupEntity terminalGroupEntity);

    TerminalGroupEntity terminalGroupToEntity(nsu.sber.domain.model.entity.TerminalGroup terminalGroup);

}
