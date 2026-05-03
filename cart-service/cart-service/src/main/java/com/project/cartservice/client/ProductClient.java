package com.project.cartservice.client;

import com.project.cartservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductClient {

    @Autowired
    private WebClient webClient;

    public ProductDto getProductById(Long productId) {

        return webClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block(); // convert async → sync
    }
}
