package com.reskov.microservice.orderservice.utils;

import com.reskov.microservice.orderservice.exception.ExternalServiceUnavailableException;
import com.reskov.microservice.orderservice.exception.NotFoundException;
import com.reskov.microservice.orderservice.exception.ProductNotInStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BadRequestResponse handleNotFoundException(final NotFoundException e) {
        log.error("404 {}", e.getMessage());
        return new BadRequestResponse(e.getMessage());
    }

    @ExceptionHandler(ProductNotInStockException.class)
    @ResponseStatus(HttpStatus.OK)
    public BadRequestResponse handleProductNotInStockException(final ProductNotInStockException e) {
        log.error("200 {}", e.getMessage());
        return new BadRequestResponse(e.getMessage());
    }

    @ExceptionHandler(ExternalServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public BadRequestResponse handleExternalServiceUnavailableException(final ExternalServiceUnavailableException e) {
        log.error("503 {}", e.getMessage());
        return new BadRequestResponse(e.getMessage());
    }
}
