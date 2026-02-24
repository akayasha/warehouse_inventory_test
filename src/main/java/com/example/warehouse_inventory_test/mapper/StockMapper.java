package com.example.warehouse_inventory_test.mapper;

import com.example.warehouse_inventory_test.dto.request.StockRequestDTO;
import com.example.warehouse_inventory_test.dto.response.StockResponseDTO;
import com.example.warehouse_inventory_test.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    Stock toEntity(StockRequestDTO dto);

    StockResponseDTO toResponse(Stock entity);
}