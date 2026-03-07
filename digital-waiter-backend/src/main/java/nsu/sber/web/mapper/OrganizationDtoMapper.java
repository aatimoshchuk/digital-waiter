package nsu.sber.web.mapper;

import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.organization.CreateOrganizationRequest;
import nsu.sber.domain.model.organization.UpdateOrganizationRequest;
import nsu.sber.web.dto.CreateOrganizationRequestDto;
import nsu.sber.web.dto.OrganizationResponseDto;
import nsu.sber.web.dto.UpdateOrganizationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrganizationDtoMapper {

    CreateOrganizationRequest dtoToCreateOrganizationRequest(CreateOrganizationRequestDto createOrganizationRequestDto);

    List<OrganizationResponseDto> organizationsToDtoList(List<Organization> organizationList);

    OrganizationResponseDto organizationToDto(Organization organization);

    UpdateOrganizationRequest dtoToUpdateOrganizationRequest(UpdateOrganizationRequestDto updateOrganizationRequestDto);

}
