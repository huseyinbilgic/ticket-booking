package com.algofusion.exception;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algofusion.common.dto.GlobalErrorResponse;
import com.algofusion.common.dto.GlobalException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.groupingBy(
                        error -> ((FieldError) error).getField(),
                        Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toList())));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleException(GlobalException exc) {
        GlobalErrorResponse globalErrorResponse = GlobalErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exc.getMessage())
                .error("BAD_REQUEST")
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(globalErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
