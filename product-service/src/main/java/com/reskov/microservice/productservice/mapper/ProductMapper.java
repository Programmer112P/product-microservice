package com.reskov.microservice.productservice.mapper;

import com.reskov.microservice.productservice.dto.PostProductDto;
import com.reskov.microservice.productservice.dto.ResponseProductDto;
import com.reskov.microservice.productservice.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product postDtoToModel(PostProductDto postProductDto) {
        return Product.builder()
                .name(postProductDto.name())
                .description(postProductDto.description())
                .price(postProductDto.price())
                .build();
    }

    public ResponseProductDto modelToResponseDto(Product product) {
        return ResponseProductDto.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    public List<ResponseProductDto> modelListToResponseDtoList(List<Product> products) {
        return products.stream().map(this::modelToResponseDto).toList();
    }
}
