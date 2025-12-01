package nsu.sber.web.controller;

import lombok.extern.slf4j.Slf4j;
import nsu.sber.exception.DigitalWaiterException;
import nsu.sber.web.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(DigitalWaiterException.class)
    public ResponseEntity<ErrorResponseDto> handleDigitalWaiterException(DigitalWaiterException e) {
        return buildErrorResponse(getHttpStatus(e), e.getMessage(), e);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus status, String message, Exception e) {
        UUID uuid = UUID.randomUUID();
        log.error("Handled {} [{}] : {}", e.getClass().getSimpleName(), uuid, message, e);

        return ResponseEntity.status(status)
                .body(ErrorResponseDto.builder()
                        .status(status.value())
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .uuid(uuid)
                        .build());
    }

    private HttpStatus getHttpStatus(DigitalWaiterException e) {
        return e.getClass().getAnnotation(ResponseStatus.class).value();
    }
}
