package com.molistore.application.exception;

import com.molistore.application.enums.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends RuntimeException {

    private ExceptionCode exceptionCode;
    private String message;

    public UserException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

    public UserException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
        this.message = exceptionCode.getDescription();
    }
}