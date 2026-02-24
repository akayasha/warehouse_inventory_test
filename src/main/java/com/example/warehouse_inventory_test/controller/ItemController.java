package com.example.warehouse_inventory_test.controller;

import com.example.warehouse_inventory_test.dto.request.ItemRequestDTO;
import com.example.warehouse_inventory_test.dto.response.ApiResponse;
import com.example.warehouse_inventory_test.dto.response.ItemResponseDTO;
import com.example.warehouse_inventory_test.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Tag(name = "items", description = "Endpoints for managing items in the warehouse inventory")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Create a new item", description = "Creates a new item in the warehouse inventory")
    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDTO>> create(
            @Valid
            @RequestBody
            ItemRequestDTO request) {

        ItemResponseDTO response = itemService.createItem(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Item created successfully",
                        response
                ));
    }

    @Operation(summary = "Get item by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemResponseDTO>> getById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Item retrieved successfully",
                        itemService.getItemById(id)
                )
        );
    }

    @Operation(summary = "Get All Items with Pagination")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ItemResponseDTO>>> getAll(
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Items retrieved",
                        itemService.getAllItems(pageable)
                )
        );
    }

    @Operation(summary = "Update Item")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemResponseDTO>> update(
            @PathVariable UUID id,
            @Valid
            @RequestBody ItemRequestDTO request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Item updated",
                        itemService.updateItem(id, request)
                ));
    }

    @Operation(summary = "Delete Item")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID id)
            throws ChangeSetPersister.NotFoundException {

        itemService.deleteItem(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Item deleted",
                        null
                ));
    }


}
