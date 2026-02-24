package com.example.warehouse_inventory_test.controller;

import com.example.warehouse_inventory_test.dto.request.VariantRequestDTO;
import com.example.warehouse_inventory_test.dto.response.ApiResponse;
import com.example.warehouse_inventory_test.dto.response.VariantResponseDTO;
import com.example.warehouse_inventory_test.service.VariantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/variants")
@Tag(name = "Variants", description = "Manage item variants")
public class VariantController {

    private final VariantService variantService;

    @Operation(summary = "Create Variant")
    @PostMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<VariantResponseDTO>> create(
            @PathVariable UUID itemId,
            @RequestBody VariantRequestDTO dto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Variant created",
                        variantService.createVariant(itemId, dto)
                ));
    }

    @Operation(summary = "Delete Variant")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID id
    ) {

        variantService.deleteVariant(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Variant deleted",
                        null
                ));
    }

    @Operation(summary = "Search Variant by SKU with Pagination")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<VariantResponseDTO>>> search(
            @RequestParam String sku,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Variants retrieved",
                        variantService.searchBySku(sku, pageable)
                ));
    }
}
