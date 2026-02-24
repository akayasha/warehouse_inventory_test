package com.example.warehouse_inventory_test.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class StockResponseDTO {

    private UUID id;

    private Integer quantity;

    public StockResponseDTO() {}

}
