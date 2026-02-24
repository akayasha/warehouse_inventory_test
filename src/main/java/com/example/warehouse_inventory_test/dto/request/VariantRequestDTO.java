package com.example.warehouse_inventory_test.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class VariantRequestDTO {


    @NotBlank
    private String sku;

    private String size;
    private String color;

    @NotNull
    private StockRequestDTO stock;

    private List<PriceRequestDTO> prices;

    public VariantRequestDTO() {}

}

