package com.example.warehouse_inventory_test.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "variants",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "sku")
        }
)
public class Variant extends BaseEntity {

    private String sku;
    private String size;
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "items_id")
    private Items item;

    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private List<Price> prices = new ArrayList<>();
}

