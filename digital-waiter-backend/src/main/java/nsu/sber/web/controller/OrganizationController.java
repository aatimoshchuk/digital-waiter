package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.service.OrganizationService;
import nsu.sber.util.ApiConstants;
import nsu.sber.web.dto.CreateOrganizationRequestDto;
import nsu.sber.web.dto.OrganizationResponseDto;
import nsu.sber.web.dto.UpdateOrganizationRequestDto;
import nsu.sber.web.mapper.OrganizationDtoMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organizations")
@Tag(name = "Organization Controller")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationDtoMapper organizationDtoMapper;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new organization",
            description = "Registers a new organization. " + ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public void createOrganization(@RequestBody @Valid CreateOrganizationRequestDto createOrganizationRequestDto) {
        organizationService.createOrganization(
                organizationDtoMapper.dtoToCreateOrganizationRequest(createOrganizationRequestDto)
        );
    }

    @GetMapping("/")
    @Operation(
            summary = "Get all organizations",
            description = "Returns a list of all organizations registered in the Digital Waiter system. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public List<OrganizationResponseDto> getOrganizations() {
        return organizationDtoMapper.organizationsToDtoList(organizationService.getOrganizations());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get organization by ID",
            description = "Returns detailed information about a specific organization. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public OrganizationResponseDto getOrganizationById(@PathVariable @Min(1) Integer id) {
        return organizationDtoMapper.organizationToDto(organizationService.getOrganizationById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an organization",
            description = "Deletes an organization. " +
                    "This operation may require that all related terminal groups and tables are removed first. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public void deleteOrganizationById(@PathVariable @Min(1) Integer id) {
        organizationService.deleteOrganizationById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an organization",
            description = "Updates the organization information. " + ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public OrganizationResponseDto updateOrganizationById(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid UpdateOrganizationRequestDto updateOrganizationRequestDto
    ) {
        Organization organization = organizationService.updateOrganizationById(
                id,
                organizationDtoMapper.dtoToUpdateOrganizationRequest(updateOrganizationRequestDto)
        );

        return organizationDtoMapper.organizationToDto(organization);
    }

}
