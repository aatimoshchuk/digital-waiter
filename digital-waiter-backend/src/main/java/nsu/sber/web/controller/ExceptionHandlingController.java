package nsu.sber.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
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

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlingController {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnknownException(Exception e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseDto> handleFeignException(FeignException e) {
        String apiMessage = extractFeignMessage(e);
        String requestInfo = extractFeignRequestInfo(e);

        String message = "An error occurred while executing the iiko request (" + requestInfo + "): " + apiMessage;

        return buildErrorResponse(HttpStatus.valueOf(e.status()), message, e);
    }

    @ExceptionHandler(DigitalWaiterException.class)
    public ResponseEntity<ErrorResponseDto> handleDigitalWaiterException(DigitalWaiterException e) {
        return buildErrorResponse(getHttpStatus(e), e.getMessage(), e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Incorrect value for one of the fields");

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

    private String extractFeignRequestInfo(FeignException e) {
        return e.request() == null ? "" : String.format(
                "%s %s",
                e.request().httpMethod(),
                URI.create(e.request().url()).getPath()
        );
    }

    private String extractFeignMessage(FeignException e) {
        String body = e.contentUTF8();

        if (body == null || body.isBlank()) {
            return e.getMessage();
        }

        try {
            JsonNode node = objectMapper.readTree(body);

            JsonNode errorDescription = node.get("errorDescription");
            if (errorDescription != null && !errorDescription.asText().isBlank()) {
                return errorDescription.asText();
            }

            return body;
        } catch (Exception ignored) {
            return body;
        }
    }
}
