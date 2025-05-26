package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.LoginRequest;
import nsu.sber.domain.service.TableAuthService;
import nsu.sber.web.dto.AuthResponseDto;
import nsu.sber.web.dto.LoginRequestDto;
import nsu.sber.web.mapper.AuthDtoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Авторизация", description = "API для авторизации в сервисе")
public class AuthController {

    private final TableAuthService tableAuthService;
    private final AuthDtoMapper authDtoMapper;
    @PostMapping("/login")
    @Operation(
            summary = "Авторизация",
            description = "Авторизация в приложении с помощью логина и пароля, выданных ресторану"
    )
    @SecurityRequirement(name = "")
    public AuthResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = authDtoMapper.dtoToLoginRequest(loginRequestDto);
        String token = tableAuthService.loginTable(loginRequest);
        return new AuthResponseDto(token);
    }
}
