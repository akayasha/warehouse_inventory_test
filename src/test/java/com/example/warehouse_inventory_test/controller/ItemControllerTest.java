package com.example.warehouse_inventory_test.controller;

import com.example.warehouse_inventory_test.dto.request.ItemRequestDTO;
import com.example.warehouse_inventory_test.dto.response.ItemResponseDTO;
import com.example.warehouse_inventory_test.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void create_returnsCreated() throws Exception {
        ItemRequestDTO request = new ItemRequestDTO();
        request.setName("New Item");
        request.setDescription("Desc");

        ItemResponseDTO response = new ItemResponseDTO();
        UUID id = UUID.randomUUID();
        response.setId(id);
        response.setName("New Item");
        response.setDescription("Desc");

        when(itemService.createItem(any(ItemRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()))
                .andExpect(jsonPath("$.data.name").value("New Item"));
    }

    @Test
    void getById_returnsItem() throws Exception {
        UUID id = UUID.randomUUID();
        ItemResponseDTO response = new ItemResponseDTO();
        response.setId(id);
        response.setName("Found");

        when(itemService.getItemById(id)).thenReturn(response);

        mockMvc.perform(get("/api/items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()))
                .andExpect(jsonPath("$.data.name").value("Found"));
    }
}
