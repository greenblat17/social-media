package com.greenblat.socialmedia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                                WebRequest webRequest) {
        var errorDetails = new ErrorDetailsResponse(
                LocalDateTime.now(),
                e.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldValidationResponse>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldValidationResponse> errors = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            var message = error.getDefaultMessage();
            var field = ((FieldError) error).getField();

            var validationResponse = new FieldValidationResponse(
                    LocalDateTime.now(),
                    field,
                    message
            );

            errors.add(validationResponse);
        });

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
