package com.example.warehouse_inventory_test.repository;

import com.example.warehouse_inventory_test.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository  extends JpaRepository<Items, UUID> {
}
