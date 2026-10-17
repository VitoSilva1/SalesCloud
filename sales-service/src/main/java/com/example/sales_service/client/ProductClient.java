package com.example.sales_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductClient {
    private final RestClient client;

    public ProductClient() {
        String productServiceUrl = System.getProperty(
                "product.service.url",
                System.getenv().getOrDefault("PRODUCT_SERVICE_URL", "http://localhost:8082"));
        this.client = RestClient.builder().baseUrl(productServiceUrl).build();
    }
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    public BigDecimal priceOf(Long productId) {
        Map<?, ?> product = client.get().uri("/products/{id}", productId).retrieve().body(Map.class);
        if (product == null || product.get("price") == null) throw new IllegalStateException("Product unavailable");
        return new BigDecimal(product.get("price").toString());
    }
    private BigDecimal fallback(Long productId, Throwable error) { return BigDecimal.ZERO; }
}
