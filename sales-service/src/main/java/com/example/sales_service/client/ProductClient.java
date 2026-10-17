package com.example.sales_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductClient {
    private final RestClient client;
    public ProductClient(RestClient.Builder builder) { this.client = builder.baseUrl("http://product-service").build(); }
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    public BigDecimal priceOf(Long productId) {
        Map<?, ?> product = client.get().uri("/products/{id}", productId).retrieve().body(Map.class);
        if (product == null || product.get("price") == null) throw new IllegalStateException("Product unavailable");
        return new BigDecimal(product.get("price").toString());
    }
    private BigDecimal fallback(Long productId, Throwable error) { return BigDecimal.ZERO; }
}
