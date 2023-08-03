package com.reskov.microservice.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseOrderDto(
        Long id,

        String orderNumber,

        @JsonProperty("orderLineItems")
        List<OrderLineItemDto> orderLineItemDtoList
) {
}
