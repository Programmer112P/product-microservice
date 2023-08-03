package com.reskov.microservice.orderservice.exception;

public class ExternalServiceUnavailableException extends OrderException{
    public ExternalServiceUnavailableException() {
    }

    public ExternalServiceUnavailableException(String message) {
        super(message);
    }

    public ExternalServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
