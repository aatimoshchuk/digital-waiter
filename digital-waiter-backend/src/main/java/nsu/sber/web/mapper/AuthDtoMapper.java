package nsu.sber.web.mapper;

import nsu.sber.domain.model.auth.ExtendTokenRequest;
import nsu.sber.domain.model.auth.GuestLogoutRequest;
import nsu.sber.domain.model.auth.JwtAuthentication;
import nsu.sber.domain.model.auth.SignInRequest;
import nsu.sber.domain.model.auth.SignInResponse;
import nsu.sber.web.dto.ExtendTokenRequestDto;
import nsu.sber.web.dto.GuestLogoutRequestDto;
import nsu.sber.web.dto.JwtAuthenticationDto;
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

    GuestLogoutRequest dtoToGuestLogoutRequest(GuestLogoutRequestDto guestLogoutRequestDto);

    ExtendTokenRequest dtoToExtendTokenRequest(ExtendTokenRequestDto extendTokenRequestDto);

    JwtAuthenticationDto jwtAuthenticationToDto(JwtAuthentication jwtAuthentication);

    @Mapping(target = "jwtAuthenticationDto", source = "jwtAuthentication")
    SignInResponseDto signInResponseToDto(SignInResponse signInResponse);

}
