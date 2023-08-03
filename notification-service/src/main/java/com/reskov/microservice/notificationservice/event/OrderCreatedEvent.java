package com.reskov.microservice.notificationservice.event;

public record OrderCreatedEvent(
        String orderNumber
) {
}
