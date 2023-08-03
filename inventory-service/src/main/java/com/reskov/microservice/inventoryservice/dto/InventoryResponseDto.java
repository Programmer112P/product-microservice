package com.reskov.microservice.inventoryservice.dto;

import lombok.Builder;

@Builder
public record InventoryResponseDto(
        String skuCode,

        Boolean isInStock
) {
}
