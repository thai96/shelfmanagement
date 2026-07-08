package com.thai.pham.inventoryservice.repository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    public ProductRepository productRepo;
    @Autowired
    public TestEntityManager entityManager;

    @BeforeEach
    public void populateProductDb() {
        List.of(
            new Product(UUID.randomUUID(), "test sku", "test product", new ProductAttributes("#000000", "1 Cup")),
            new Product(UUID.randomUUID(), "test sku", "test product 2", new ProductAttributes("#123456", "1 package")),
            new Product(UUID.randomUUID(), "test sku", "dif prefix test p", new ProductAttributes("#658392", "1 box")),
            new Product(UUID.randomUUID(), "test sku", "product test", new ProductAttributes("#FABC23", "1000 ml"))
        ).forEach(product -> {
            entityManager.persist(product);
        });
        entityManager.flush();
    }

    @Test
    public void testFindProductByProductNameContaining_EmptyInput() {
        List<Product> finalResult = List.of(
            new Product(UUID.randomUUID(), "test sku", "test product", new ProductAttributes("#000000", "1 Cup")),
            new Product(UUID.randomUUID(), "test sku", "test product 2", new ProductAttributes("#123456", "1 package")),
            new Product(UUID.randomUUID(), "test sku", "dif prefix test product", new ProductAttributes("#658392", "1 box")),
            new Product(UUID.randomUUID(), "test sku", "product test", new ProductAttributes("#FABC23", "1000 ml"))
        );
        int expectedTotalElement = 4;
        int expectedPageIdx = 0;

        String emptySearchTerm = "";
        Page<Product> result = productRepo.findProductByProductNameContaining(emptySearchTerm, Pageable.first());

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
        Assertions.assertEquals(expectedSize, result.getSize());
        Assertions.assertIterableEquals(expectedElement, result.getContent());
    }

    public static Stream<Arguments> createSearchDataWithPrefixTest() {
        return Stream.of(
            Arguments.of(
                "product 2", 1, 1, Pageable.first(), List.of(new Product(UUID.randomUUID(), "test sku", "test product 2", new ProductAttributes("#123456", "1 package"))),
            ),
            Arguments.of(
                "product", 3, 3, Pageable.first(), List.of(new Product(UUID.randomUUID(), "test sku", "test product", new ProductAttributes("#000000", "1 Cup")),
                new Product(UUID.randomUUID(), "test sku", "test product 2", new ProductAttributes("#123456", "1 package")),
                new Product(UUID.randomUUID(), "test sku", "product test", new ProductAttributes("#FABC23", "1000 ml"))
            )),
            Arguments.of("Unknown", 0, 0, Pageable.first(), List.of())
        );
    }

    
}