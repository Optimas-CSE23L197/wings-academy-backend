package com.elearning.gateway.exception;

public class ServiceUnavailableException extends ApiException {

    public ServiceUnavailableException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
