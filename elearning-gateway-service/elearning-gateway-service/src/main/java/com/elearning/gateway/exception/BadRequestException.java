package com.elearning.gateway.exception;

public class BadRequestException extends ApiException {

    public BadRequestException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
