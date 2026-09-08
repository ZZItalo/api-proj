package com.italo.task_api.advice;

import com.italo.task_api.dto.ErrorDetailsDto;
import com.italo.task_api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

        ErrorDetailsDto errorDetails = new ErrorDetailsDto();

        String msg = "The API could not accept the request because its "
                +"request body can not be read or converted to a "
                +"Java object. Try checking for: Malformed JSON "
                +"syntax (missing commas, unclosed brackets, etc), "
                +"data type mismatches, invalid enum values, etc.";

        errorDetails.setMessage(msg);

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetailsDto> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        ErrorDetailsDto errorDetails = new ErrorDetailsDto();

        StringBuilder sb = new StringBuilder();
        for(ObjectError ex : exception.getBindingResult().getAllErrors()) {
            if(ex.getDefaultMessage() != null){

                if(!sb.isEmpty()){
                    sb.append(", ");
                }
                sb.append(ex.getDefaultMessage());
            }
        }

        String msg = sb.toString();

        errorDetails.setMessage(msg);

        return ResponseEntity.badRequest().body(errorDetails);
    }
}
