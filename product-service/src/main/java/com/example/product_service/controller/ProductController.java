package com.example.product_service.controller;

import com.example.product_service.model.Product;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }
    @GetMapping public List<Product> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Product findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product saved = service.save(product);
        return ResponseEntity.created(URI.create("/products/" + saved.getId())).body(saved);
    }
    @PutMapping("/{id}") public Product update(@PathVariable Long id, @Valid @RequestBody Product product) { return service.update(id, product); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
