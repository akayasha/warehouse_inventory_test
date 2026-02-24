package com.example.warehouse_inventory_test.controller;

import com.example.warehouse_inventory_test.dto.request.VariantRequestDTO;
import com.example.warehouse_inventory_test.dto.response.VariantResponseDTO;
import com.example.warehouse_inventory_test.service.VariantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VariantController.class)
class VariantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VariantService variantService;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void createVariant_returnsCreated() throws Exception {
        UUID itemId = UUID.randomUUID();
        VariantRequestDTO request = new VariantRequestDTO();
        request.setSku("SKU-1");

        VariantResponseDTO response = new VariantResponseDTO();
        UUID id = UUID.randomUUID();
        response.setId(id);
        response.setSku("SKU-1");

        when(variantService.createVariant(eq(itemId), any(VariantRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/variants/item/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()))
                .andExpect(jsonPath("$.data.sku").value("SKU-1"));
    }

    @Test
    void deleteVariant_returnsOk() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/variants/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void search_returnsPage() throws Exception {
        String sku = "ABC";
        VariantResponseDTO dto = new VariantResponseDTO();
        dto.setSku("ABC-1");

        when(variantService.searchBySku(eq(sku), any())).thenReturn(new PageImpl<>(Collections.singletonList(dto), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/variants/search").param("sku", sku))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.content[0].sku").value("ABC-1"));
    }
}
