package com.example.announcmentapp.exception.handler;

import com.example.announcmentapp.exception.ExceptionConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
public class ExceptionResponse {
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
