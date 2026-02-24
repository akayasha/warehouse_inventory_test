package com.example.warehouse_inventory_test.controller;

import com.example.warehouse_inventory_test.dto.request.StockRequestDTO;
import com.example.warehouse_inventory_test.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InventoryService inventoryService;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void addStock_returnsOk() throws Exception {
        UUID variantId = UUID.randomUUID();
        StockRequestDTO request = new StockRequestDTO();
        request.setQuantity(5);

        doNothing().when(inventoryService).addStock(variantId, 5);

        mockMvc.perform(post("/api/inventory/{variantId}/add", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(inventoryService).addStock(variantId, 5);
    }

    @Test
    void sell_returnsOk() throws Exception {
        UUID variantId = UUID.randomUUID();
        StockRequestDTO request = new StockRequestDTO();
        request.setQuantity(3);

        doNothing().when(inventoryService).sell(variantId, 3);

        mockMvc.perform(post("/api/inventory/{variantId}/sell", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(inventoryService).sell(variantId, 3);
    }
}
