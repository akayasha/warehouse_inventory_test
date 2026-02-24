package com.example.warehouse_inventory_test.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "prices")
public class Price extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;

}
