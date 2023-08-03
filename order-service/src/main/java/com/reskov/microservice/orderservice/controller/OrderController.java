package com.reskov.microservice.orderservice.controller;

import com.reskov.microservice.orderservice.dto.PostOrderDto;
import com.reskov.microservice.orderservice.dto.ResponseOrderDto;
import com.reskov.microservice.orderservice.mapper.OrderMapper;
import com.reskov.microservice.orderservice.model.Order;
import com.reskov.microservice.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public String create(@RequestBody @Valid PostOrderDto postOrderDto) {
        Order order = orderMapper.postOrderDtoToModel(postOrderDto);
        orderService.create(order);
        return "Order successfully created";
    }

    @GetMapping
    public List<ResponseOrderDto> getPage(
            @RequestParam(required = false, defaultValue = "0") final Integer offset,
            @RequestParam(required = false, defaultValue = "20") final Integer size) {
        List<Order> page = orderService.getPage(offset, size);
        return orderMapper.modelListToResponseDtoList(page);
    }
}
