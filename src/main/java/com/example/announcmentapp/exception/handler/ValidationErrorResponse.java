package com.example.announcmentapp.exception.handler;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ValidationErrorResponse {
    private String field;
    private String message;
}
