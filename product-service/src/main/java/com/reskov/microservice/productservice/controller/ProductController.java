package com.reskov.microservice.productservice.controller;

import com.reskov.microservice.productservice.dto.PostProductDto;
import com.reskov.microservice.productservice.dto.ResponseProductDto;
import com.reskov.microservice.productservice.mapper.ProductMapper;
import com.reskov.microservice.productservice.model.Product;
import com.reskov.microservice.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseProductDto create(@RequestBody @Valid final PostProductDto postProductDto) {
        Product productToCreate = productMapper.postDtoToModel(postProductDto);
        Product created = productService.create(productToCreate);
        return productMapper.modelToResponseDto(created);
    }

    @GetMapping
    public List<ResponseProductDto> getPage(
            @RequestParam(required = false, defaultValue = "0") final Integer offset,
            @RequestParam(required = false, defaultValue = "20") final Integer size) {
        List<Product> productsPage = productService.getPage(offset, size);
        return productMapper.modelListToResponseDtoList(productsPage);
    }
}
