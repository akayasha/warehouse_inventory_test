package com.example.warehouse_inventory_test.mapper;

import com.example.warehouse_inventory_test.dto.request.ItemRequestDTO;
import com.example.warehouse_inventory_test.dto.response.ItemResponseDTO;
import com.example.warehouse_inventory_test.entity.Items;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = VariantMapper.class)
public interface ItemMapper {

    Items toEntity(ItemRequestDTO dto);

    ItemResponseDTO toResponse(Items entity);

}
