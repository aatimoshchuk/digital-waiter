package nsu.sber.db.mapper;

import nsu.sber.db.entity.TerminalGroupEntity;
import nsu.sber.domain.model.entity.TerminalGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TerminalGroupEntityMapper {

    TerminalGroup entityToTerminalGroup(TerminalGroupEntity terminalGroup);

    TerminalGroupEntity terminalGroupToEntity(TerminalGroup terminalGroup);

}
