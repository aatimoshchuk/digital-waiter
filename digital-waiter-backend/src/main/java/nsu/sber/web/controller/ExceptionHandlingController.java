package nsu.sber.web.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.exception.DigitalWaiterException;
import nsu.sber.web.dto.ErrorResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Incorrect value for one of the fields");
//        String messages = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> formatPatternMessage(error.getField(), error.getDefaultMessage()))
//                .collect(Collectors.joining("; "));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, message, e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handlePathVariableValidationException(ConstraintViolationException e) {
        String messages = e.getConstraintViolations()
                .stream()
                .map(violation -> formatPatternMessage(
                        violation.getPropertyPath().toString(),
                        violation.getMessage())
                )
                .collect(Collectors.joining("; "));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, messages, e);
    }

    private String formatPatternMessage(String fieldName, String message) {
        if (message != null && message.contains("\"")) {
            String pattern = message.substring(message.indexOf("\"") + 1, message.lastIndexOf("\""));
            return String.format("Field '%s' should match pattern: %s.", fieldName, pattern);
        }
        return String.format("Incorrect value for the '%s' field.", fieldName);
    }

    private HttpStatus getHttpStatus(DigitalWaiterException e) {
        return e.getClass().getAnnotation(ResponseStatus.class).value();
    }
}
