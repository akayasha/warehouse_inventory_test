package com.example.warehouse_inventory_test.service;

import com.example.warehouse_inventory_test.dto.request.ItemRequestDTO;
import com.example.warehouse_inventory_test.dto.response.ItemResponseDTO;
import com.example.warehouse_inventory_test.entity.Items;
import com.example.warehouse_inventory_test.exceptions.NotFoundException;
import com.example.warehouse_inventory_test.mapper.ItemMapper;
import com.example.warehouse_inventory_test.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    @Test
    void createItem_success() {
        ItemRequestDTO dto = new ItemRequestDTO();
        dto.setName("Test Item");
        dto.setDescription("A test item");

        Items entity = new Items();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        Items saved = new Items();
        UUID id = UUID.randomUUID();
        saved.setId(id);
        saved.setName(dto.getName());
        saved.setDescription(dto.getDescription());

        ItemResponseDTO responseDTO = new ItemResponseDTO();
        responseDTO.setId(id);
        responseDTO.setName(dto.getName());
        responseDTO.setDescription(dto.getDescription());

        when(itemMapper.toEntity(dto)).thenReturn(entity);
        when(itemRepository.save(entity)).thenReturn(saved);
        when(itemMapper.toResponse(saved)).thenReturn(responseDTO);

        ItemResponseDTO result = itemService.createItem(dto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(dto.getName(), result.getName());
        verify(itemRepository, times(1)).save(entity);
        verify(itemMapper, times(1)).toEntity(dto);
        verify(itemMapper, times(1)).toResponse(saved);
    }

    @Test
    void getItemById_success() {
        UUID id = UUID.randomUUID();
        Items item = new Items();
        item.setId(id);
        item.setName("Found Item");

        ItemResponseDTO responseDTO = new ItemResponseDTO();
        responseDTO.setId(id);
        responseDTO.setName("Found Item");

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemMapper.toResponse(item)).thenReturn(responseDTO);

        ItemResponseDTO result = itemService.getItemById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Found Item", result.getName());
        verify(itemRepository).findById(id);
        verify(itemMapper).toResponse(item);
    }

    @Test
    void getItemById_notFound_throws() {
        UUID id = UUID.randomUUID();
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> itemService.getItemById(id));
        assertTrue(ex.getMessage().contains("Item not found"));
        verify(itemRepository).findById(id);
        verifyNoMoreInteractions(itemMapper);
    }
}

