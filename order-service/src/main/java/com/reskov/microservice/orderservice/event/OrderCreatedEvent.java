package com.reskov.microservice.orderservice.event;

public record OrderCreatedEvent(
        String orderNumber
) {
}
