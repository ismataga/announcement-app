package com.example.announcmentapp.exception;


import com.example.announcmentapp.exception.handler.ValidationErrorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class AppException extends RuntimeException {
    private final String userMessage;
    private final HttpStatus httpStatus;

    public AppException(ExceptionConstants exceptionConstants, String errorMessage) {
        super(errorMessage);
        this.userMessage = exceptionConstants.getUserMessage();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

    public AppException(Object metadata, ExceptionConstants exceptionConstants) {
        super(resolveMessage(metadata, exceptionConstants)); // Handle metadata conversion here
        this.userMessage = exceptionConstants.getUserMessage();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

    private static String resolveMessage(Object metadata, ExceptionConstants exceptionConstants) {
        if (metadata instanceof String) {
            return exceptionConstants.getUserMessage().concat((String) metadata);
        } else {
            return exceptionConstants.getUserMessage().concat(metadata.toString()); // Use toString() for reliable conversion
        }
    }

    public AppException(ExceptionConstants exceptionConstants) {
        super(exceptionConstants.getUserMessage());
        this.userMessage = exceptionConstants.getUserMessage();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

    @Data
    @SuperBuilder
    public static class ExceptionResponse {
        private String userMessage;
        private String errorMessage;
        private List<ValidationErrorResponse> validationErrors;
        @Builder.Default
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime date = LocalDateTime.now();

        public ExceptionResponse(ExceptionConstants exceptionConstants, String errorMessage) {
            this.errorMessage = errorMessage;
            this.userMessage = exceptionConstants.getUserMessage();
        }
    }
}
