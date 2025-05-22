package com.pita.auth_service.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pita.auth_service.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("Errores de validación");
        log.warn("Error de validacion en {}: {}", request.getDescription(false), errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", errorMessage, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("Errores de validación");
        log.warn("Error de validacion en {}: {}", request.getDescription(false), errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", errorMessage, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex, WebRequest request) {
        log.error("Error de acceso a datos en {}: {}", request.getDescription(false), ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("DATA_ACCESS_ERROR", "Error al acceder a la base de datos",
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(JWTVerificationException ex) {
        log.warn("Error de validación de JWT: {}", ex.getMessage());
        return ResponseEntity.status(401)
                .body(new ErrorResponse("INVALID_TOKEN", "Token inválido o expirado", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.warn("Error de argumento en {}: {}", request.getDescription(false), ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("INVALID_REQUEST", ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error("Error inesperado en {}: {}", request.getDescription(false), ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_ERROR", "Error interno del servidor", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        log.error("Error de integridad de datos en {}: {}", request.getDescription(false), ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("DATA_INTEGRITY_VIOLATION", "Nombre de Usuario o email ya en uso",
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
