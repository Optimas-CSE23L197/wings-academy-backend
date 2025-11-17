package com.elearning.gateway.exception;

public class TokenInvalidException extends ApiException {

    public TokenInvalidException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
