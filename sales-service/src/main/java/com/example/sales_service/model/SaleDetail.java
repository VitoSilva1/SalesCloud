package com.example.sales_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class SaleDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotNull private Long productId;
    @Min(1) private int quantity;
    private BigDecimal unitPrice;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "sale_id") @JsonIgnore private Sale sale;
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Sale getSale() { return sale; }
    public void setSale(Sale sale) { this.sale = sale; }
}
