package com.reskov.microservice.inventoryservice.service;

import com.reskov.microservice.inventoryservice.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {

    public List<InventoryResponseDto> isInStock(List<String> skuCode);
}
