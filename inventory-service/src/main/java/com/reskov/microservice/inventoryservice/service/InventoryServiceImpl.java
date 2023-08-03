package com.reskov.microservice.inventoryservice.service;

import com.reskov.microservice.inventoryservice.dto.InventoryResponseDto;
import com.reskov.microservice.inventoryservice.model.Inventory;
import com.reskov.microservice.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDto> isInStock(List<String> skuCode) {
        log.info("isInStock: skuCode {}", skuCode);
        List<Inventory> products = inventoryRepository.findBySkuCodeIn(skuCode);
        List<InventoryResponseDto> response = getInventoryResponses(products);
        log.info("isInStock: skuCode {} - {}", skuCode, response);
        return response;
    }

    private List<InventoryResponseDto> getInventoryResponses(List<Inventory> inventoryList) {
        return inventoryList.stream()
                .map(inventory ->
                        InventoryResponseDto.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                )
                .toList();
    }
}
