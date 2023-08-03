package com.reskov.microservice.productservice.dto;

import lombok.Builder;

@Builder
public record ResponseProductDto(
        String id,
        String name,
        String description,
        Long price
) {
}
