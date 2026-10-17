package com.example.sales_service.controller;

import com.example.sales_service.model.Sale;
import com.example.sales_service.service.SaleService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/sales")
public class SaleController {
    private final SaleService service;
    public SaleController(SaleService service) { this.service = service; }
    @GetMapping public List<Sale> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Sale findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public ResponseEntity<Sale> create(@Valid @RequestBody Sale sale) { Sale saved = service.save(sale); return ResponseEntity.created(URI.create("/sales/" + saved.getId())).body(saved); }
    @PutMapping("/{id}") public Sale update(@PathVariable Long id, @Valid @RequestBody Sale sale) { return service.update(id, sale); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
