package com.reskov.microservice.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderLineItemDto(

        Long id,

        @NotEmpty
        String skuCode,

        @NotNull
        Long price,

        Integer quantity
) {
}
