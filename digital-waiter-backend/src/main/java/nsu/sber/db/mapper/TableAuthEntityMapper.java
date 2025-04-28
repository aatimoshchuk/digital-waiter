package nsu.sber.db.mapper;

import nsu.sber.db.entity.TableAuthEntity;
import nsu.sber.domain.model.TableAuth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TableAuthEntityMapper {

    TableAuth entityToTableAuth(TableAuthEntity tableAuth);

    TableAuthEntity tableAuthToEntity(TableAuth tableAuth);
}
