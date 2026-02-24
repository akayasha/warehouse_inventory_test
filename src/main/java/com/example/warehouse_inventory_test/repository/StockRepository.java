package com.example.warehouse_inventory_test.repository;

import com.example.warehouse_inventory_test.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findByVariantId(UUID variantId);
}
