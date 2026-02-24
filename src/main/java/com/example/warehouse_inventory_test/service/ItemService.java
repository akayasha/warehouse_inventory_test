package com.example.warehouse_inventory_test.service;

import com.example.warehouse_inventory_test.dto.request.ItemRequestDTO;
import com.example.warehouse_inventory_test.dto.response.ItemResponseDTO;
import com.example.warehouse_inventory_test.entity.Items;
import com.example.warehouse_inventory_test.exceptions.NotFoundException;
import com.example.warehouse_inventory_test.mapper.ItemMapper;
import com.example.warehouse_inventory_test.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Transactional
    public ItemResponseDTO createItem(ItemRequestDTO dto) {
        Items item = itemMapper.toEntity(dto);

        if (item.getVariants() != null) {
            item.getVariants().forEach(variant -> {
                variant.setItem(item);

                if (variant.getStock() != null) {
                    variant.getStock().setVariant(variant);
                }

                if (variant.getPrices() != null) {
                    variant.getPrices().forEach(price -> price.setVariant(variant));
                }
            });
        }

        Items savedItem = itemRepository.save(item);
        return itemMapper.toResponse(savedItem);
    }

    public ItemResponseDTO getItemById(UUID id) {
        return itemRepository.findById(id)
                .map(itemMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));
    }

    public Page<ItemResponseDTO> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable).map(itemMapper::toResponse);
    }

    @Transactional
    public ItemResponseDTO updateItem(UUID id, ItemRequestDTO dto) {
        Items existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        existingItem.setName(dto.getName());
        existingItem.setDescription(dto.getDescription());

        return itemMapper.toResponse(itemRepository.save(existingItem));
    }

    @Transactional
    public void deleteItem(UUID id) {
        if (!itemRepository.existsById(id)) {
            throw new NotFoundException("Item not found");
        }
        itemRepository.deleteById(id);
    }
}