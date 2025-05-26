package nsu.sber.web.controller;

import lombok.extern.slf4j.Slf4j;
import nsu.sber.exception.AuthException;
import nsu.sber.exception.ServiceException;
import nsu.sber.web.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> handleServiceException(ServiceException e) {
        log.error("Запрос завершен с ошибкой: ServiceException", e);
        String sErrorType = Optional.ofNullable(e.getErrorType())
                .map(Enum::name)
                .orElse(null);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .errorType(sErrorType)
                        .build());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthException(AuthException e) {
        log.error("Запрос завершен с ошибкой: " +
                "пользователь не имеет достаточных прав или не авторизован в системе", e);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }
}
