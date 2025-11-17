package com.elearning.gateway.exception;

public class NotFoundException extends ApiException {

    public NotFoundException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
