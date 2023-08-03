package com.reskov.microservice.orderservice.service;

import com.reskov.microservice.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    void create(Order order);

    List<Order> getPage(Integer offset, Integer size);
}
