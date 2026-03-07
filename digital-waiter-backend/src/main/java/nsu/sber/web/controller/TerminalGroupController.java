package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.TerminalGroupService;
import nsu.sber.util.ApiConstants;
import nsu.sber.web.dto.CreateTerminalGroupRequestDto;
import nsu.sber.web.dto.TerminalGroupResponseDto;
import nsu.sber.web.dto.UpdateTerminalGroupRequestDto;
import nsu.sber.web.mapper.TerminalGroupDtoMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terminal-groups")
@Tag(name = "Terminal Group Controller")
public class TerminalGroupController {
    private final TerminalGroupService terminalGroupService;
    private final TerminalGroupDtoMapper terminalGroupDtoMapper;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new terminal group",
            description = "Registers a new terminal group. " + ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public void createTerminalGroup(@RequestBody @Valid CreateTerminalGroupRequestDto createTerminalGroupRequestDto) {
        terminalGroupService.createTerminalGroup(
                terminalGroupDtoMapper.dtoToCreateTerminalGroupRequest(createTerminalGroupRequestDto)
        );
    }

    @GetMapping("/")
    @Operation(
            summary = "Get terminal groups by organization ID",
            description = "Returns a list of terminal groups filtered by organization ID. " +
                    "If the organizationId parameter is not provided, returns all terminal groups. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public List<TerminalGroupResponseDto> getTerminalGroups(
            @RequestParam(name = "organizationId", required = false) @Min(1) Integer organizationId
    ) {
        return terminalGroupDtoMapper.terminalGroupsToDtoList(terminalGroupService.getTerminalGroups(organizationId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get terminal group by ID",
            description = "Returns detailed information about a specific terminal group. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public TerminalGroupResponseDto getTerminalGroupById(@PathVariable @Min(1) Integer id) {
        return terminalGroupDtoMapper.terminalGroupToDto(terminalGroupService.getTerminalGroup(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a terminal group",
            description = "Deletes a terminal group. " +
                    "This operation may require that all related tables are removed or reassigned first. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public void deleteTerminalGroupById(@PathVariable @Min(1) Integer id) {
        terminalGroupService.deleteTerminalGroup(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a terminal group",
            description = "Updates the terminal group information. " + ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public TerminalGroupResponseDto updateTerminalGroupById(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid UpdateTerminalGroupRequestDto updateTerminalGroupRequestDto
    ) {
        return terminalGroupDtoMapper.terminalGroupToDto(terminalGroupService.updateTerminalGroup(
                id,
                terminalGroupDtoMapper.dtoToUpdateTerminalGroupRequest(updateTerminalGroupRequestDto)
        ));
    }
}
