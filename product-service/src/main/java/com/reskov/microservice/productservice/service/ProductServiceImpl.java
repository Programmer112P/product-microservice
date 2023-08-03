package com.reskov.microservice.productservice.service;

import com.reskov.microservice.productservice.model.Product;
import com.reskov.microservice.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product productToCreate) {
        log.info("createProduct: request {}", productToCreate);
        Product createdProduct = productRepository.save(productToCreate);
        log.info("createProduct: response {}", createdProduct);
        return createdProduct;
    }

    @Override
    public List<Product> getPage(Integer offset, Integer size) {
        log.info("getPage: request offset {}, size {}", offset, size);
        List<Product> products = productRepository.findAll(PageRequest.of(offset, size)).getContent();
        log.info("getPage: completed request offset {}, size {}", offset, size);
        return products;
    }
}
