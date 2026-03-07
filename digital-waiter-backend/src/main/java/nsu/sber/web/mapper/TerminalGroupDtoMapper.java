package nsu.sber.web.mapper;

import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.terminal_group.CreateTerminalGroupRequest;
import nsu.sber.domain.model.terminal_group.UpdateTerminalGroupRequest;
import nsu.sber.web.dto.CreateTerminalGroupRequestDto;
import nsu.sber.web.dto.TerminalGroupResponseDto;
import nsu.sber.web.dto.UpdateTerminalGroupRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TerminalGroupDtoMapper {

    CreateTerminalGroupRequest dtoToCreateTerminalGroupRequest(
            CreateTerminalGroupRequestDto createTerminalGroupRequestDto
    );

    UpdateTerminalGroupRequest dtoToUpdateTerminalGroupRequest(
            UpdateTerminalGroupRequestDto updateTerminalGroupRequestDto
    );

    List<TerminalGroupResponseDto> terminalGroupsToDtoList(List<TerminalGroup> terminalGroups);

    TerminalGroupResponseDto terminalGroupToDto(TerminalGroup terminalGroup);
}
