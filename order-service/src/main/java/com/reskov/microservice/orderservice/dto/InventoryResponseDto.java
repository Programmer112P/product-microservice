package com.reskov.microservice.orderservice.dto;

import lombok.Builder;

@Builder
public record InventoryResponseDto(
        String skuCode,

        Boolean isInStock
) {
}
