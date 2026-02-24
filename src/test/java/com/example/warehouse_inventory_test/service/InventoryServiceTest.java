package com.example.warehouse_inventory_test.service;

import com.example.warehouse_inventory_test.entity.Stock;
import com.example.warehouse_inventory_test.exceptions.InsufficientStockException;
import com.example.warehouse_inventory_test.exceptions.NotFoundException;
import com.example.warehouse_inventory_test.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void addStock_success() {
        UUID variantId = UUID.randomUUID();
        Stock stock = new Stock();
        stock.setId(UUID.randomUUID());
        stock.setQuantity(10);

        when(stockRepository.findByVariantId(variantId)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenAnswer(i -> i.getArgument(0));

        inventoryService.addStock(variantId, 5);

        assertEquals(15, stock.getQuantity());
        verify(stockRepository).findByVariantId(variantId);
        verify(stockRepository).save(stock);
    }

    @Test
    void addStock_notFound_throws() {
        UUID variantId = UUID.randomUUID();
        when(stockRepository.findByVariantId(variantId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> inventoryService.addStock(variantId, 5));
        assertTrue(ex.getMessage().contains("Stock records not found"));
        verify(stockRepository).findByVariantId(variantId);
    }

    @Test
    void sell_success() {
        UUID variantId = UUID.randomUUID();
        Stock stock = new Stock();
        stock.setId(UUID.randomUUID());
        stock.setQuantity(10);

        when(stockRepository.findByVariantId(variantId)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenAnswer(i -> i.getArgument(0));

        inventoryService.sell(variantId, 4);

        assertEquals(6, stock.getQuantity());
        verify(stockRepository).findByVariantId(variantId);
        verify(stockRepository).save(stock);
    }

    @Test
    void sell_insufficient_throws() {
        UUID variantId = UUID.randomUUID();
        Stock stock = new Stock();
        stock.setId(UUID.randomUUID());
        stock.setQuantity(2);

        when(stockRepository.findByVariantId(variantId)).thenReturn(Optional.of(stock));

        InsufficientStockException ex = assertThrows(InsufficientStockException.class, () -> inventoryService.sell(variantId, 5));
        assertTrue(ex.getMessage().contains("Stok tidak cukup"));
        verify(stockRepository).findByVariantId(variantId);
        verify(stockRepository, never()).save(any());
    }
}

