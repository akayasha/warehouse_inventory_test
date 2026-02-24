package com.example.warehouse_inventory_test.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceRequestDTO {


    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String currency;

    private boolean active;

    public PriceRequestDTO() {}
}
