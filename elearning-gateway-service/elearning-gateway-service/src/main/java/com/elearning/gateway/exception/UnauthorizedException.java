package com.elearning.gateway.exception;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
