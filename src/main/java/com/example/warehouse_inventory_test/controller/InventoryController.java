package com.example.warehouse_inventory_test.controller;

import com.example.warehouse_inventory_test.dto.request.StockRequestDTO;
import com.example.warehouse_inventory_test.dto.response.ApiResponse;
import com.example.warehouse_inventory_test.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Stock management APIs")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "Add Stock")
    @PostMapping("/{variantId}/add")
    public ResponseEntity<ApiResponse<Void>> addStock(
            @PathVariable UUID variantId,
            @RequestBody StockRequestDTO request) {

        inventoryService.addStock(variantId, request.getQuantity());

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Stock added",
                        null
                ));
    }

    @Operation(summary = "Sell Stock")
    @PostMapping("/{variantId}/sell")
    public ResponseEntity<ApiResponse<Void>> sell(
            @PathVariable UUID variantId,
            @RequestBody StockRequestDTO request) {

        inventoryService.sell(variantId, request.getQuantity());

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Stock updated",
                        null
                ));
    }

}
