package nsu.sber.web.mapper;

import nsu.sber.domain.model.LoginRequest;
import nsu.sber.web.dto.LoginRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuthDtoMapper {

    LoginRequest dtoToLoginRequest(LoginRequestDto loginRequestDto);

}
