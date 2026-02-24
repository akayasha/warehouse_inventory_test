package com.example.warehouse_inventory_test.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stocks")
@Data
public class Stock extends BaseEntity{


    @Column(nullable = false)
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;
}

