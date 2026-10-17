package com.example.sales_service.exception;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> invalid(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage(), (a,b) -> a));
        return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "fields", errors));
    }
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<Map<String, String>> error(RuntimeException ex) { return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage() == null ? "Request failed" : ex.getMessage())); }
}
