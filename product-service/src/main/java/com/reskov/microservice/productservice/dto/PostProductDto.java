package com.reskov.microservice.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostProductDto(

        @NotBlank
        String name,

        String description,

        @NotBlank
        Long price
) {
}
