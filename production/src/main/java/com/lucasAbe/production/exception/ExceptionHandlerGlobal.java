package com.lucasAbe.production.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class ExceptionHandlerGlobal {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErros(MethodArgumentNotValidException ex){

        List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + " " + error.getDefaultMessage())
        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Validation Error", errors));

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleJsonErrors(HttpMessageNotReadableException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Malformed JSON", List.of("Invalid JSON or incorrect field sent")));
    }

}
