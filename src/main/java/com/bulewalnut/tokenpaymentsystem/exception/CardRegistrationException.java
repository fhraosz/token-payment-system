package com.bulewalnut.tokenpaymentsystem.exception;

public class CardRegistrationException extends RuntimeException {

    public CardRegistrationException(String message) {
        super(message);
    }

    public CardRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}