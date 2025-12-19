package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.auth.SignInResponse;
import nsu.sber.web.dto.ExtendTokenRequestDto;
import nsu.sber.web.dto.GuestLogoutRequestDto;
import nsu.sber.web.dto.JwtAuthenticationDto;
import nsu.sber.web.dto.SignInRequestDto;
import nsu.sber.web.dto.SignInResponseDto;
import nsu.sber.web.mapper.AuthDtoMapper;
import nsu.sber.web.security.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authorization and Authentication Controller")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final AuthDtoMapper authDtoMapper;

    @PostMapping("/sign-in")
    @Operation(
            summary = "Authentication",
            description = "Authenticates a user of the guest / administrative interface"
    )
    @SecurityRequirements({})
    public SignInResponseDto signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
       SignInResponse signInResponse = authenticationService
               .signIn(authDtoMapper.dtoToSignInRequest(signInRequestDto));

       return authDtoMapper.signInResponseToDto(signInResponse);
    }

    @PostMapping("/extend-token")
    @Operation(summary = "Extend access token by refresh token")
    @SecurityRequirements({})
    public JwtAuthenticationDto extendToken(@RequestBody @Valid ExtendTokenRequestDto extendTokenRequestDto) {
        return authDtoMapper.jwtAuthenticationToDto(
                authenticationService.extendToken(authDtoMapper.dtoToExtendTokenRequest(extendTokenRequestDto))
        );
    }

    @PostMapping("/guest/logout")
    @Operation(
            summary = "Logout from guest interface",
            description = """
                    Logs out the guest session associated with the current table.
                    Password confirmation is required to prevent accidental or unauthorized logout
                    """
    )
    public void logout(@RequestBody @Valid GuestLogoutRequestDto guestLogoutRequestDto) {
        authenticationService.guestLogout(authDtoMapper.dtoToGuestLogoutRequest(guestLogoutRequestDto));
    }

}
