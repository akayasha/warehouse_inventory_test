package com.example.warehouse_inventory_test.mapper;

import com.example.warehouse_inventory_test.dto.request.VariantRequestDTO;
import com.example.warehouse_inventory_test.dto.response.VariantResponseDTO;
import com.example.warehouse_inventory_test.entity.Variant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StockMapper.class, PriceMapper.class})
public interface VariantMapper {

    Variant toEntity(VariantRequestDTO dto);

    VariantResponseDTO toResponse(Variant entity);

}
