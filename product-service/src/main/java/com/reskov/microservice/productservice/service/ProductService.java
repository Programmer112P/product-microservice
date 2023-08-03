package com.reskov.microservice.productservice.service;

import com.reskov.microservice.productservice.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product productToCreate);

    List<Product> getPage(Integer offset, Integer size);
}
