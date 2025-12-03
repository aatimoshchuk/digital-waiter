package nsu.sber.db.mapper;

import javax.annotation.processing.Generated;
import nsu.sber.db.entity.TerminalGroupEntity;
import nsu.sber.domain.model.entity.TerminalGroup;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-03T12:15:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class TerminalGroupEntityMapperImpl implements TerminalGroupEntityMapper {

    @Override
    public TerminalGroup entityToTerminalGroup(TerminalGroupEntity terminalGroup) {
        if ( terminalGroup == null ) {
            return null;
        }

        TerminalGroup.TerminalGroupBuilder terminalGroup1 = TerminalGroup.builder();

        terminalGroup1.id( terminalGroup.getId() );
        terminalGroup1.posExternalMenuId( terminalGroup.getPosExternalMenuId() );
        terminalGroup1.posTerminalGroupId( terminalGroup.getPosTerminalGroupId() );
        terminalGroup1.organizationId( terminalGroup.getOrganizationId() );

        return terminalGroup1.build();
    }

    @Override
    public TerminalGroupEntity terminalGroupToEntity(TerminalGroup terminalGroup) {
        if ( terminalGroup == null ) {
            return null;
        }

        TerminalGroupEntity terminalGroupEntity = new TerminalGroupEntity();

        terminalGroupEntity.setId( terminalGroup.getId() );
        terminalGroupEntity.setPosExternalMenuId( terminalGroup.getPosExternalMenuId() );
        terminalGroupEntity.setPosTerminalGroupId( terminalGroup.getPosTerminalGroupId() );
        terminalGroupEntity.setOrganizationId( terminalGroup.getOrganizationId() );

        return terminalGroupEntity;
    }
}
