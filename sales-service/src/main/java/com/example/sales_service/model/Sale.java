package com.example.sales_service.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private Long customerId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private BigDecimal total = BigDecimal.ZERO;
    @NotEmpty @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true) @Valid
    private List<SaleDetail> details = new ArrayList<>();
    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public List<SaleDetail> getDetails() { return details; }
    public void setDetails(List<SaleDetail> details) { this.details = details; details.forEach(d -> d.setSale(this)); }
}
