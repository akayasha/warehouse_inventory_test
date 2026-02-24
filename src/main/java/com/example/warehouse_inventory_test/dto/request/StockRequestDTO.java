package com.example.warehouse_inventory_test.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class StockRequestDTO {

    @Min(0)
    private Integer quantity;

    public StockRequestDTO() {}

}
