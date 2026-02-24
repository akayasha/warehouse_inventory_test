package com.example.warehouse_inventory_test.service;

import com.example.warehouse_inventory_test.dto.request.VariantRequestDTO;
import com.example.warehouse_inventory_test.dto.response.VariantResponseDTO;
import com.example.warehouse_inventory_test.entity.Items;
import com.example.warehouse_inventory_test.entity.Variant;
import com.example.warehouse_inventory_test.exceptions.NotFoundException;
import com.example.warehouse_inventory_test.mapper.VariantMapper;
import com.example.warehouse_inventory_test.repository.ItemRepository;
import com.example.warehouse_inventory_test.repository.VariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VariantServiceTest {

    @Mock
    private VariantRepository variantRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private VariantMapper variantMapper;

    @InjectMocks
    private VariantService variantService;

    @Test
    void createVariant_success() {
        UUID itemId = UUID.randomUUID();
        VariantRequestDTO dto = new VariantRequestDTO();
        dto.setSku("SKU1");

        Items item = new Items();
        item.setId(itemId);

        Variant variant = new Variant();
        variant.setSku(dto.getSku());

        Variant saved = new Variant();
        UUID vId = UUID.randomUUID();
        saved.setId(vId);
        saved.setSku(dto.getSku());

        VariantResponseDTO response = new VariantResponseDTO();
        response.setId(vId);
        response.setSku(dto.getSku());

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(variantMapper.toEntity(dto)).thenReturn(variant);
        when(variantRepository.save(variant)).thenReturn(saved);
        when(variantMapper.toResponse(saved)).thenReturn(response);

        VariantResponseDTO result = variantService.createVariant(itemId, dto);

        assertNotNull(result);
        assertEquals(vId, result.getId());
        assertEquals("SKU1", result.getSku());
        verify(itemRepository).findById(itemId);
        verify(variantRepository).save(variant);
    }

    @Test
    void createVariant_itemNotFound_throws() {
        UUID itemId = UUID.randomUUID();
        VariantRequestDTO dto = new VariantRequestDTO();

        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> variantService.createVariant(itemId, dto));
        verify(itemRepository).findById(itemId);
        verifyNoInteractions(variantRepository);
    }

    @Test
    void searchBySku_returnsPaged() {
        String sku = "ABC";
        Variant variant = new Variant();
        variant.setSku("ABC-1");
        VariantResponseDTO responseDTO = new VariantResponseDTO();
        responseDTO.setSku("ABC-1");

        Page<Variant> page = new PageImpl<>(Collections.singletonList(variant), PageRequest.of(0, 10), 1);
        when(variantRepository.findBySkuContainingIgnoreCase(eq(sku), any())).thenReturn(page);
        when(variantMapper.toResponse(variant)).thenReturn(responseDTO);

        Page<VariantResponseDTO> result = variantService.searchBySku(sku, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("ABC-1", result.getContent().get(0).getSku());
    }

    @Test
    void deleteVariant_delegates() {
        UUID id = UUID.randomUUID();
        variantService.deleteVariant(id);
        verify(variantRepository).deleteById(id);
    }
}

