package com.italo.task_api.advice;

import com.italo.task_api.dto.ErrorDetailsDto;
import com.italo.task_api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(
            ResourceNotFoundException exception) {

        ErrorDetailsDto errorDetails = new ErrorDetailsDto();

        errorDetails.setMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetailsDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {

        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto();

        String msg = "The API could not accept the request because its "
                +"request body can not be read or converted to a "
                +"Java object. Try checking for: Malformed JSON "
                +"syntax (missing commas, unclosed brackets, etc), "
                +"data type mismatches, invalid enum values, etc.";

        errorDetailsDto.setMessage(msg);

        return ResponseEntity.badRequest().body(errorDetailsDto);
    }
}
