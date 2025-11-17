package com.elearning.gateway.exception;

public class RateLimitExceededException extends ApiException {

    public RateLimitExceededException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
