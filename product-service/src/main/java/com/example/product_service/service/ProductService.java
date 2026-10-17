package com.example.product_service.service;

import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;
    public ProductService(ProductRepository repository) { this.repository = repository; }
    public List<Product> findAll() { return repository.findAll(); }
    public Product findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id)); }
    public Product save(Product product) { return repository.save(product); }
    public Product update(Long id, Product input) {
        Product product = findById(id);
        product.setName(input.getName()); product.setDescription(input.getDescription());
        product.setPrice(input.getPrice()); product.setStock(input.getStock());
        return repository.save(product);
    }
    public void delete(Long id) { repository.delete(findById(id)); }
}
