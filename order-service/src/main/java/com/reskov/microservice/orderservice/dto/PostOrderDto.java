package com.reskov.microservice.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record PostOrderDto(

        @NotEmpty
        @JsonProperty("orderLineItems")
        List<OrderLineItemDto> orderLineItemDtoList
) {
}
