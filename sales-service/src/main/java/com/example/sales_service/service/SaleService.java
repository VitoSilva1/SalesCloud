package com.example.sales_service.service;

import com.example.sales_service.client.ProductClient;
import com.example.sales_service.model.*;
import com.example.sales_service.repository.SaleRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    private final SaleRepository repository; private final ProductClient productClient;
    public SaleService(SaleRepository repository, ProductClient productClient) { this.repository = repository; this.productClient = productClient; }
    public List<Sale> findAll() { return repository.findAll(); }
    public Sale findById(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found: " + id)); }
    public Sale save(Sale sale) { BigDecimal total = BigDecimal.ZERO; for (SaleDetail detail : sale.getDetails()) { detail.setSale(sale); BigDecimal price = productClient.priceOf(detail.getProductId()); if (price.signum() <= 0) throw new IllegalArgumentException("Product price unavailable: " + detail.getProductId()); detail.setUnitPrice(price); total = total.add(price.multiply(BigDecimal.valueOf(detail.getQuantity()))); } sale.setTotal(total); return repository.save(sale); }
    public Sale update(Long id, Sale input) { Sale existing = findById(id); existing.setCustomerId(input.getCustomerId()); existing.setDetails(input.getDetails()); return save(existing); }
    public void delete(Long id) { repository.delete(findById(id)); }
}
