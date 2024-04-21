package com.example.announcmentapp.exception.handler;


import com.example.announcmentapp.exception.AppException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.announcmentapp.exception.ExceptionConstants.METHOD_NOT_ALLOWED_EXCEPTION;
import static com.example.announcmentapp.exception.ExceptionConstants.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.*;


@Log4j2
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public AppException.ExceptionResponse handle(Exception ex) {
        log.error("Exception: ", ex);
        return new AppException.ExceptionResponse(UNEXPECTED_EXCEPTION, ex.getMessage());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppException.ExceptionResponse> handle(AppException ex) {
        log.error("ResourceNotFoundException: ", ex);

        AppException.ExceptionResponse exceptionResponse = AppException.ExceptionResponse.builder()
                .errorMessage(ex.getMessage())
                .userMessage(ex.getUserMessage())
                .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public AppException.ExceptionResponse handle(MethodNotAllowedException ex) {
        log.error("MethodNotAllowedException: ", ex);
        return new AppException.ExceptionResponse(METHOD_NOT_ALLOWED_EXCEPTION, ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AppException.ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: ", ex);
        List<ValidationErrorResponse> errorsForBadRequest = getErrorsForBadRequest(ex);
        return AppException.ExceptionResponse.builder()
                .userMessage("Method arguments not valid")
                .errorMessage(ex.getMessage())
                .validationErrors(errorsForBadRequest)
                .build();
    }

    private List<ValidationErrorResponse> getErrorsForBadRequest(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> ValidationErrorResponse.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
    }
}
