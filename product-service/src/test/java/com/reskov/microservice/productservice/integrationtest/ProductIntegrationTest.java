package com.reskov.microservice.productservice.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reskov.microservice.productservice.dto.PostProductDto;
import com.reskov.microservice.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Container
    protected static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource()
    protected static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Test
    void create_shouldReturn200_whenCreateTest() throws Exception {
        PostProductDto postProductDto = getPostProductDto();
        String requestBody = objectMapper.writeValueAsString(postProductDto);

        mockMvc.perform(post("/api/product")
                        .header("Content-Type", "application/json")
                        .content(requestBody))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name").value("test product"),
                        jsonPath("$.description").value("test desc"),
                        jsonPath("$.price").value(500)
                );
        assertThat(productRepository.findAll().size()).isEqualTo(1);
    }

    private PostProductDto getPostProductDto() {
        return PostProductDto.builder()
                .name("test product")
                .description("test desc")
                .price(500L)
                .build();
    }
}
