package com.example.warehouse_inventory_test.dto.response;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ItemResponseDTO {

    private UUID id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private List<VariantResponseDTO> variants;

    public ItemResponseDTO() {}



}
