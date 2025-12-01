package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.TableAuthService;
import nsu.sber.web.dto.JwtAuthenticationDto;
import nsu.sber.web.dto.SignInRequestDto;
import nsu.sber.web.mapper.AuthDtoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authorization and Authentication Controller")
public class AuthController {

    private final TableAuthService tableAuthService;
    private final AuthDtoMapper authDtoMapper;

    @PostMapping("/sign-in")
    @Operation(
            summary = "Sign in",
            description = "Sign in with login and password, which restaurant received from technical support"
    )
    @SecurityRequirements({})
    public JwtAuthenticationDto signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        return new JwtAuthenticationDto(
                tableAuthService.loginTable(authDtoMapper.dtoToSignInRequest(signInRequestDto))
        );
    }

}
