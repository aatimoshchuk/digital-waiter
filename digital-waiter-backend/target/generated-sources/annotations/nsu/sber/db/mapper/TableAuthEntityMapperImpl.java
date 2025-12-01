package nsu.sber.db.mapper;

import javax.annotation.processing.Generated;
import nsu.sber.db.entity.TableAuthEntity;
import nsu.sber.domain.model.entity.TableAuth;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T20:45:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class TableAuthEntityMapperImpl implements TableAuthEntityMapper {

    @Override
    public TableAuth entityToTableAuth(TableAuthEntity tableAuth) {
        if ( tableAuth == null ) {
            return null;
        }

        TableAuth.TableAuthBuilder tableAuth1 = TableAuth.builder();

        tableAuth1.id( tableAuth.getId() );
        tableAuth1.login( tableAuth.getLogin() );
        tableAuth1.password( tableAuth.getPassword() );

        return tableAuth1.build();
    }

    @Override
    public TableAuthEntity tableAuthToEntity(TableAuth tableAuth) {
        if ( tableAuth == null ) {
            return null;
        }

        TableAuthEntity tableAuthEntity = new TableAuthEntity();

        tableAuthEntity.setId( tableAuth.getId() );
        tableAuthEntity.setLogin( tableAuth.getLogin() );
        tableAuthEntity.setPassword( tableAuth.getPassword() );

        return tableAuthEntity;
    }
}
