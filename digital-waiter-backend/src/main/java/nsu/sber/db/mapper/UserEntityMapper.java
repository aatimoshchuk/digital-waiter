package nsu.sber.db.mapper;

import nsu.sber.db.entity.UserEntity;
import nsu.sber.domain.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEntityMapper {

    public abstract User entityToUser(UserEntity userEntity);

}
