package com.elearning.gateway.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", ex.getStatus(),
                        "error", ex.getErrorCode(),
                        "message", ex.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity
        .status(500)
                .body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                        "status", 500,
                        "error", "INTERNAL_SERVER_ERROR",
                        "message", ex.getMessage()
                ));
    }
}
