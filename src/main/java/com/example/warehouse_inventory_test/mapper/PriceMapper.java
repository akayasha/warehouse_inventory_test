package com.example.warehouse_inventory_test.mapper;

import com.example.warehouse_inventory_test.dto.request.PriceRequestDTO;
import com.example.warehouse_inventory_test.dto.response.PriceResponseDTO;
import com.example.warehouse_inventory_test.entity.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toEntity(PriceRequestDTO dto);

    PriceResponseDTO toResponse(Price entity);
}