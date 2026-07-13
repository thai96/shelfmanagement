package com.thai.pham.inventoryservice;

import com.thai.pham.inventoryservice.dto.InventoryDto;
import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.repository.InventoryRepository;
import com.thai.pham.inventoryservice.service.InventoryService;
import com.thai.pham.inventoryservice.controller.InventoryController;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.stream.Stream;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("getInventoryPageData")
    public void testGetInventoryPage(PageDto<InventoryDto> expectedOutput, Page<Inventory> outputOfRepo, Map<String, String> pageData) throws Exception {
        when(inventoryRepository.findAll(any(Pageable.class))).thenReturn(outputOfRepo);

        String expectedDataAsJson = objectMapper.writeValueAsString(expectedOutput);
        MockHttpServletRequestBuilder requestBuilder = get("api/v1/inventory/");
        pageData.forEach(requestBuilder::param);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedDataAsJson));
    }

    public static Stream<Arguments> getInventoryPageData() {
        return Stream.of(

        );
    }
}