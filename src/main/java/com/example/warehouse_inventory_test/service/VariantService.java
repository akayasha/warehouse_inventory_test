package com.example.warehouse_inventory_test.service;

import com.example.warehouse_inventory_test.dto.request.VariantRequestDTO;
import com.example.warehouse_inventory_test.dto.response.VariantResponseDTO;
import com.example.warehouse_inventory_test.entity.Items;
import com.example.warehouse_inventory_test.entity.Variant;
import com.example.warehouse_inventory_test.exceptions.NotFoundException;
import com.example.warehouse_inventory_test.mapper.VariantMapper;
import com.example.warehouse_inventory_test.repository.ItemRepository;
import com.example.warehouse_inventory_test.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VariantService {

    private final VariantRepository variantRepository;
    private final ItemRepository itemRepository;
    private final VariantMapper variantMapper;

    @Transactional
    public VariantResponseDTO createVariant(UUID itemId, VariantRequestDTO dto) {
        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        Variant variant = variantMapper.toEntity(dto);
        variant.setItem(item);

        if (variant.getStock() != null) {
            variant.getStock().setVariant(variant);
        }
        if (variant.getPrices() != null) {
            variant.getPrices().forEach(price -> price.setVariant(variant));
        }

        Variant saved = variantRepository.save(variant);
        return variantMapper.toResponse(saved);
    }

    public Page<VariantResponseDTO> searchBySku(String sku, Pageable pageable) {
        return variantRepository.findBySkuContainingIgnoreCase(sku, pageable)
                .map(variantMapper::toResponse);
    }

    @Transactional
    public void deleteVariant(UUID id) {
        variantRepository.deleteById(id);
    }
}