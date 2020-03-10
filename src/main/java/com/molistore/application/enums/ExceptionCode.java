package com.molistore.application.enums;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    UNKNOWN_EXCEPTION("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
    NPE_EXCEPTION("NPE errors, temporal problem", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_PATTERN_EXCEPTION("Email don't match by userName structure", HttpStatus.BAD_REQUEST),
    MATCH_PASSWORD_EXCEPTION("Password don't match", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_EXCEPTION("Password don't match", HttpStatus.UNAUTHORIZED),
    EMAIL_EXIST_EXCEPTION("Email allready exist", HttpStatus.BAD_REQUEST),
    PASSWORD_STRENGTH_EXCEPTION("Password isn't strength", HttpStatus.BAD_REQUEST),
    VALIDATION_REQUEST_EXCEPTION("Request not valid", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND_EXCEPTION("User not found ", HttpStatus.NOT_FOUND);

    private final String description;
    private final HttpStatus httpStatus;

    ExceptionCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }


    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

