package com.example.warehouse_inventory_test.service;

import com.example.warehouse_inventory_test.entity.Stock;
import com.example.warehouse_inventory_test.exceptions.InsufficientStockException;
import com.example.warehouse_inventory_test.exceptions.NotFoundException;
import com.example.warehouse_inventory_test.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StockRepository stockRepository;

    @Transactional
    public void addStock(UUID variantId, Integer quantity) {
        Stock stock = stockRepository.findByVariantId(variantId)
                .orElseThrow(() -> new NotFoundException("Stock records not found for variant: " + variantId));

        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
    }

    @Transactional
    public void sell(UUID variantId, Integer quantity) {
        Stock stock = stockRepository.findByVariantId(variantId)
                .orElseThrow(() -> new NotFoundException("Stock records not found for variant ID: " + variantId));

        if (stock.getQuantity() < quantity) {
            throw new InsufficientStockException("Stok tidak cukup! Tersedia: " + stock.getQuantity());
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }
}