package nsu.sber.db.mapper;

import nsu.sber.db.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrganizationEntityMapper {

    nsu.sber.domain.model.entity.Organization entityToOrganization(OrganizationEntity organizationEntity);

    OrganizationEntity organizationToEntity(nsu.sber.domain.model.entity.Organization organization);

}
