package nsu.sber.db.mapper;

import javax.annotation.processing.Generated;
import nsu.sber.db.entity.OrganizationEntity;
import nsu.sber.domain.model.entity.Organization;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T20:45:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class OrganizationEntityMapperImpl implements OrganizationEntityMapper {

    @Override
    public Organization entityToOrganization(OrganizationEntity organization) {
        if ( organization == null ) {
            return null;
        }

        Organization.OrganizationBuilder organization1 = Organization.builder();

        organization1.id( organization.getId() );
        organization1.apiKey( organization.getApiKey() );
        organization1.posOrganizationId( organization.getPosOrganizationId() );

        return organization1.build();
    }

    @Override
    public OrganizationEntity organizationToEntity(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        OrganizationEntity organizationEntity = new OrganizationEntity();

        organizationEntity.setId( organization.getId() );
        organizationEntity.setApiKey( organization.getApiKey() );
        organizationEntity.setPosOrganizationId( organization.getPosOrganizationId() );

        return organizationEntity;
    }
}
