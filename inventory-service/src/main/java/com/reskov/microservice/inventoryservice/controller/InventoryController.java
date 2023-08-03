package com.reskov.microservice.inventoryservice.controller;

import com.reskov.microservice.inventoryservice.dto.InventoryResponseDto;
import com.reskov.microservice.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponseDto> isInStock(@RequestParam("skuCode") final List<String> skuCodeList) {
        return inventoryService.isInStock(skuCodeList);
    }
}
