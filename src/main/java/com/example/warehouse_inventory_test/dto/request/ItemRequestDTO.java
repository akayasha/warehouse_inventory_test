package com.example.warehouse_inventory_test.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ItemRequestDTO {

    @NotBlank
    private String name;

    private String description;

    private List<VariantRequestDTO> variants;

    public ItemRequestDTO() {}

}
