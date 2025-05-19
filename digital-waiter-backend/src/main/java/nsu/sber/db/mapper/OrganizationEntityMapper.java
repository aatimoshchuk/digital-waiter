package nsu.sber.db.mapper;

import nsu.sber.db.entity.OrganizationEntity;
import nsu.sber.domain.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrganizationEntityMapper {

    Organization entityToOrganization(OrganizationEntity organization);

    OrganizationEntity organizationToEntity(Organization organization);

}
