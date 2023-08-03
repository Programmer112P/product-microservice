package com.reskov.microservice.orderservice.mapper;

import com.reskov.microservice.orderservice.dto.OrderLineItemDto;
import com.reskov.microservice.orderservice.dto.PostOrderDto;
import com.reskov.microservice.orderservice.dto.ResponseOrderDto;
import com.reskov.microservice.orderservice.model.Order;
import com.reskov.microservice.orderservice.model.OrderLineItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderMapper {

    public Order postOrderDtoToModel(PostOrderDto postOrderDto) {
        List<OrderLineItem> orderLineItemList = getOrderLineItemList(postOrderDto.orderLineItemDtoList());
        return Order.builder()
                .orderLineItems(orderLineItemList)
                .orderNumber(UUID.randomUUID().toString())
                .build();
    }

    private List<OrderLineItem> getOrderLineItemList(List<OrderLineItemDto> orderLineItemDtoList) {
        return orderLineItemDtoList.stream().map(this::getOrderLineItem).toList();
    }

    private OrderLineItem getOrderLineItem(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
                .id(orderLineItemDto.id())
                .price(orderLineItemDto.price())
                .skuCode(orderLineItemDto.skuCode())
                .quantity(orderLineItemDto.quantity())
                .build();
    }

    public List<ResponseOrderDto> modelListToResponseDtoList(List<Order> orders) {
        return orders.stream().map(this::modelToResponseDto).toList();
    }

    public ResponseOrderDto modelToResponseDto(Order order) {
        List<OrderLineItemDto> orderLineItemDtoList = getOrderLineItemDtoList(order.getOrderLineItems());
        return ResponseOrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemDtoList(orderLineItemDtoList)
                .build();
    }

    private List<OrderLineItemDto> getOrderLineItemDtoList(List<OrderLineItem> orderLineItemList) {
        return orderLineItemList.stream().map(this::getOrderLineItemDto).toList();
    }

    private OrderLineItemDto getOrderLineItemDto(OrderLineItem orderLineItem) {
        return OrderLineItemDto.builder()
                .id(orderLineItem.getId())
                .skuCode(orderLineItem.getSkuCode())
                .quantity(orderLineItem.getQuantity())
                .price(orderLineItem.getPrice())
                .build();
    }
}
