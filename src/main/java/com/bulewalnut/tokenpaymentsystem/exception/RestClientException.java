package com.bulewalnut.tokenpaymentsystem.exception;

public class RestClientException extends RuntimeException {

    public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }
}