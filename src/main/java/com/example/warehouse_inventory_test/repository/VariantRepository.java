package com.example.warehouse_inventory_test.repository;

import com.example.warehouse_inventory_test.entity.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VariantRepository extends JpaRepository<Variant, UUID> {

    Page<Variant> findBySkuContainingIgnoreCase(String sku, Pageable pageable);


}
