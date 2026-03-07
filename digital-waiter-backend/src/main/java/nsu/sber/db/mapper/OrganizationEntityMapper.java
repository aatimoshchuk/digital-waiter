package nsu.sber.db.mapper;

import nsu.sber.db.entity.OrganizationEntity;
import nsu.sber.domain.model.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = TerminalGroupEntityMapper.class)
public interface OrganizationEntityMapper {

    Organization entityToOrganization(OrganizationEntity organizationEntity);

    OrganizationEntity organizationToEntity(Organization organization);

    List<Organization> entitiesToOrganizations(List<OrganizationEntity> entityList);

}
