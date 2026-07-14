package com.thai.pham.inventoryservice;

import com.thai.pham.inventoryservice.configs.DataSourceConfig;
import com.thai.pham.inventoryservice.configs.RedisCacheConfig;
import com.thai.pham.inventoryservice.configs.TestDataWebConfig;
import com.thai.pham.inventoryservice.dto.InventoryDto;
import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.entity.*;
import com.thai.pham.inventoryservice.mapper.InventoryDtoMapper;
import com.thai.pham.inventoryservice.mapper.LocationDtoMapper;
import com.thai.pham.inventoryservice.mapper.ProductDtoMapper;
import com.thai.pham.inventoryservice.service.InventoryService;
import com.thai.pham.inventoryservice.controller.InventoryController;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
        controllers = InventoryController.class,
        excludeAutoConfiguration = {
                DataWebAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                DataSourceConfig.class,
                                RedisCacheConfig.class
                        }
                )
        }
)
@Import(TestDataWebConfig.class) public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Autowired
    private JsonMapper jsonMapper;

    private static InventoryDtoMapper mapper = new InventoryDtoMapper(new ProductDtoMapper(), new LocationDtoMapper());

    @ParameterizedTest
    @MethodSource("getInventoryPageData")
    public void testGetInventoryPage(PageDto<InventoryDto> expectedOutput, Map<String, String> pageData) throws Exception {
        when(inventoryService.getAllInventory(any(Pageable.class))).thenReturn(expectedOutput);

        String expectedDataAsJson = jsonMapper.writeValueAsString(expectedOutput);
        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/inventory/");
        pageData.forEach(requestBuilder::param);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedDataAsJson));
    }

    public static Stream<Arguments> getInventoryPageData() {
        Map<String, String> firstPageData = Map.of("page", "0", "size", "3");
        List<Inventory> expectedReturnInventory = List.of(
                new Inventory(UUID.randomUUID(), 12, 10, 2, new Location(UUID.randomUUID(),"Warehouse 1",  LocationType.WARE_HOUSE, true), new Product(UUID.randomUUID(), "sku", "product name", new ProductAttributes("color", "size"))),
                new Inventory(UUID.randomUUID(), 109, 29, 99, new Location(UUID.randomUUID(),"Store 1",  LocationType.STORE, true), new Product(UUID.randomUUID(), "sku", "product name", new ProductAttributes("color", "size"))),
                new Inventory(UUID.randomUUID(), 107, 7, 100, new Location(UUID.randomUUID(),"Warehouse 2",  LocationType.WARE_HOUSE, true), new Product(UUID.randomUUID(), "sku", "product name", new ProductAttributes("color", "size")))
        );
        return Stream.of(
            Arguments.of(new PageDto<>(expectedReturnInventory.stream().map(mapper::mapObject).toList(), 0, 10, 4L, 1L, true, true),
                    firstPageData)
        );
    }
}