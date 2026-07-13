package com.thai.pham.persistent_data;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.thai.pham.inventoryservice.controller.InventoryController;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @ParameterizedTest
    public void testGetInventoryPage(PageDto<InventoryDto> expectedOutput, Map<String, String> pageData) {
        mockMvc.perform(get("api/v1/inventory/"));
        pageData.forEach((paramsName, paramsData) -> {
            mockMvc.param(paramsName, paramsData);
        });
        mockMvc.contentType(ContentType.APPLICATION_JSON)
        .andExpect(status().isOk());
    }

    
}