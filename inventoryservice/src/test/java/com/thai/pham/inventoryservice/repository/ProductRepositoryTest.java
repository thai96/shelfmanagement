package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.entity.ProductAttributes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    public ProductRepository productRepo;
    @Autowired
    public TestEntityManager entityManager;

    private static final List<Product> products = Arrays.asList(
            new Product(null, "test sku 1", "test product", new ProductAttributes("#000000", "1 Cup")),
            new Product(null, "test sku 2", "test product 2", new ProductAttributes("#123456", "1 package")),
            new Product(null, "test sku 3", "dif prefix test p", new ProductAttributes("#658392", "1 box")),
            new Product(null, "test sku 4", "product test", new ProductAttributes("#FABC23", "1000 ml"))
    );

    @BeforeEach
    public void populateProductDb() {
        productRepo.deleteAll();
        products.replaceAll(entity -> entityManager.persist(entity));
        entityManager.flush();
    }

    @AfterEach
    public void removeProductDb() {
        products.forEach(entityManager::remove);
        entityManager.flush();
        products.forEach(item -> item.setId(null));
    }

    @Test
    public void testFindProductByProductNameContaining_EmptyInput() {
        List<Product> finalResult = new ArrayList<>(products);
        int expectedTotalElement = 4;
        int expectedPageIdx = 0;

        String emptySearchTerm = "";
        Page<Product> result = productRepo.findProductByProductNameContaining(emptySearchTerm, Pageable.ofSize(10).first());

        Assertions.assertEquals(expectedTotalElement, result.getTotalElements());
        Assertions.assertEquals(expectedPageIdx, result.getNumber());
        Assertions.assertIterableEquals(finalResult, result.getContent());
    }

    @ParameterizedTest
    @MethodSource("createSearchDataWithPrefixTest")
    public void testFindProductByProductNameContaining_WithInput(
        String searchTerm,
        int expectedTotalElement,
        int expectedSize,
        Pageable pageable,
        List<Product> expectedElement
    ) {
        Page<Product> result = productRepo.findProductByProductNameContaining(searchTerm, pageable);

        Assertions.assertEquals(expectedTotalElement, result.getTotalElements());
        Assertions.assertEquals(expectedSize, result.getNumberOfElements());
        Assertions.assertIterableEquals(expectedElement, result.getContent());
    }

    public static Stream<Arguments> createSearchDataWithPrefixTest() {
        return Stream.of(
            Arguments.of(
                "product 2", 1, 1, Pageable.ofSize(10).first(), List.of(products.get(1))
            ),
            Arguments.of(
                "product", 3, 3, Pageable.ofSize(10).first(), List.of(
                        products.get(0), products.get(1), products.get(3)
                    )),
            Arguments.of("Unknown", 0, 0, Pageable.ofSize(10).first(), new ArrayList<Product>())
        );
    }

    
}