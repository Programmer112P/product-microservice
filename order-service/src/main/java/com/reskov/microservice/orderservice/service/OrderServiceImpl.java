package com.reskov.microservice.orderservice.service;

import com.reskov.microservice.orderservice.dto.InventoryResponseDto;
import com.reskov.microservice.orderservice.event.OrderCreatedEvent;
import com.reskov.microservice.orderservice.exception.ExternalServiceUnavailableException;
import com.reskov.microservice.orderservice.exception.NotFoundException;
import com.reskov.microservice.orderservice.exception.ProductNotInStockException;
import com.reskov.microservice.orderservice.model.Order;
import com.reskov.microservice.orderservice.model.OrderLineItem;
import com.reskov.microservice.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, WebClient.Builder webClientBuilder, Tracer tracer, KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.tracer = tracer;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public void create(Order order) {
        log.info("create order {}", order.getId());
        List<String> skuCodeList = order.getOrderLineItems().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();
        InventoryResponseDto[] inventoryResponses = getInventoryResponses(skuCodeList);
        if (inventoryResponses.length != skuCodeList.size()) {
            throw new NotFoundException("Product skuCode does not exist");
        }
        boolean isAllProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponseDto::isInStock);
        if (isAllProductsInStock) {
            Order created = orderRepository.save(order);
            log.info("created order {}", created.getId());
            kafkaTemplate.send("notificationTopic", new OrderCreatedEvent(created.getOrderNumber()));
        } else {
            throw new ProductNotInStockException("Product is not in stock. Please try again later");
        }
    }

    private InventoryResponseDto[] getInventoryResponses(List<String> skuCodeList) {
        Span inventoryServiceLookup = tracer.nextSpan().name("inventoryServiceLookup");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())) {
            inventoryServiceLookup.event("Lol_even_lol");
            return webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                    .retrieve()
                    .bodyToMono(InventoryResponseDto[].class)
                    .block();
        } catch (Exception e) {
            throw new ExternalServiceUnavailableException("Oops");
        }
    }

    private void fallbackMethod(Order order, ExternalServiceUnavailableException e) {
        throw new ExternalServiceUnavailableException("Oops");
    }

    @Override
    public List<Order> getPage(Integer offset, Integer size) {
        log.info("getPage: request offset {}, size {}", offset, size);
        List<Order> orders = orderRepository.findAll(PageRequest.of(offset, size)).getContent();
        log.info("getPage: complete request offset {}, size {}", offset, size);
        return orders;
    }
}
