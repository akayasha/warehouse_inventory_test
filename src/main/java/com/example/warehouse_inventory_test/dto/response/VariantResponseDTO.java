package com.example.warehouse_inventory_test.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VariantResponseDTO {

    private UUID id;

    private String sku;

    private String size;

    private String color;

    private StockResponseDTO stock;

    private List<PriceResponseDTO> prices;

    public VariantResponseDTO() {}


}
