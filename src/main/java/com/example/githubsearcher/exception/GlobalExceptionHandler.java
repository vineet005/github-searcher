package com.example.githubsearcher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 *
 * <p>
 * This class centralizes error handling across all REST controllers
 * using {@link RestControllerAdvice}. It ensures consistent and
 * structured error responses for:
 * <ul>
 *     <li>GitHub API related failures</li>
 *     <li>Validation errors</li>
 *     <li>Unexpected server errors</li>
 * </ul>
 * </p>
 *
 * <p>
 * All error responses follow a consistent JSON structure containing:
 * <ul>
 *     <li>timestamp</li>
 *     <li>status</li>
 *     <li>error or errors</li>
 * </ul>
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions thrown when GitHub API communication fails.
     *
     * <p>
     * Converts {@link GitHubApiException} into a
     * {@code 502 Bad Gateway} response.
     * </p>
     *
     * @param ex GitHubApiException
     * @return standardized error response
     */
    @ExceptionHandler(GitHubApiException.class)
    public ResponseEntity<Object> handleGitHubApiException(GitHubApiException ex) {

        return buildResponse(
                HttpStatus.BAD_GATEWAY,
                ex.getMessage()
        );
    }

    /**
     * Handles validation failures triggered by {@code @Valid}
     * annotated request DTOs.
     *
     * <p>
     * Collects field-level validation errors and returns
     * a {@code 400 Bad Request} response with detailed messages.
     * </p>
     *
     * @param ex MethodArgumentNotValidException
     * @return validation error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "errors", errors
        ));
    }

    /**
     * Handles any uncaught exceptions not explicitly handled elsewhere.
     *
     * <p>
     * Returns a generic {@code 500 Internal Server Error}
     * without exposing internal implementation details.
     * </p>
     *
     * @param ex Exception
     * @return standardized error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong. Please try again later."
        );
    }

    /**
     * Builds a standardized error response structure.
     *
     * @param status  HTTP status
     * @param message Error message
     * @return ResponseEntity with structured JSON body
     */
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {

        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now(),
                "status", status.value(),
                "error", message
        ));
    }
}
