package com.example.announcmentapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstants {

    // common exceptions
    UNEXPECTED_EXCEPTION("unexpected exception", INTERNAL_SERVER_ERROR),
    METHOD_NOT_ALLOWED_EXCEPTION("method not allowed exception", METHOD_NOT_ALLOWED),
    METHOD_ARGUMENT_NOT_VALID("method argument not valid", BAD_REQUEST),
    NOT_FOUND_EXCEPTION("", NOT_FOUND),

    //user-service
    USER_NOT_FOUND("user not found with username: ", NOT_FOUND),
    STUDENT_NOT_FOUND("student not found with username: ", NOT_FOUND),
    ANNOUNCEMENT_NOT_FOUND("question not found with id: ", NOT_FOUND),



    ;

    private final String userMessage;
    private final HttpStatus httpStatus;
}