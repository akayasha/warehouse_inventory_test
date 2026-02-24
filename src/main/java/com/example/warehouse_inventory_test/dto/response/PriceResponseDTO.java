package com.example.warehouse_inventory_test.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PriceResponseDTO {

    private UUID id;

    private BigDecimal amount;

    private String currency;

    private boolean active;

    public PriceResponseDTO() {}
}
