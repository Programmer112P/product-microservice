package com.reskov.microservice.orderservice.exception;

public class ProductNotInStockException extends OrderException{
    public ProductNotInStockException() {
    }

    public ProductNotInStockException(String message) {
        super(message);
    }

    public ProductNotInStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotInStockException(Throwable cause) {
        super(cause);
    }
}
