package com.molistore.application.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
        super("There is an account with that email address");
    }

    public EmailExistsException(String message) {
        super(message);
    }

}
