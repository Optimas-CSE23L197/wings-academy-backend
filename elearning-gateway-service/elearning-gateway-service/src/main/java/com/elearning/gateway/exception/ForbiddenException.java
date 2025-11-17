package com.elearning.gateway.exception;

public class ForbiddenException extends ApiException {

    public ForbiddenException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
