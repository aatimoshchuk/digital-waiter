package nsu.sber.web.mapper;

import nsu.sber.domain.model.auth.SignInRequest;
import nsu.sber.domain.model.auth.SignInResponse;
import nsu.sber.web.dto.SignInRequestDto;
import nsu.sber.web.dto.SignInResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuthDtoMapper {

    SignInRequest dtoToSignInRequest(SignInRequestDto signInRequestDto);

    @Mapping(target = "jwtAuthenticationDto", source = "jwtAuthentication")
    SignInResponseDto signInResponseToDto(SignInResponse signInResponse);

}
