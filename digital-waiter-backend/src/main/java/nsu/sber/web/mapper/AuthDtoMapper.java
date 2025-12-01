package nsu.sber.web.mapper;

import nsu.sber.domain.model.auth.SignInRequest;
import nsu.sber.web.dto.SignInRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuthDtoMapper {

    SignInRequest dtoToSignInRequest(SignInRequestDto signInRequestDto);

}
