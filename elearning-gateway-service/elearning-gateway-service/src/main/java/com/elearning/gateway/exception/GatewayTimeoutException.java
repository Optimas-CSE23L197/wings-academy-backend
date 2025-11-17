package com.elearning.gateway.exception;

public class GatewayTimeoutException extends ApiException {

    public GatewayTimeoutException(String message, String errorCode, int status) {
        super(message, errorCode, status);
    }
    
}
