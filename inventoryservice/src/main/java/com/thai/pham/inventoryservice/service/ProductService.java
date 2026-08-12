package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.common.exception.ProductDeletionConflictException;
import com.thai.pham.inventoryservice.dto.*;
import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.entity.ProductAttributes;
import com.thai.pham.inventoryservice.keygenerator.ProductKeyGenerator;
import com.thai.pham.inventoryservice.mapper.ProductAttributeMapper;
import com.thai.pham.inventoryservice.mapper.ProductResultMapper;
import com.thai.pham.inventoryservice.mapper.ProductUpdateDtoMapper;
import com.thai.pham.inventoryservice.repository.ProductRepository;
import com.thai.pham.inventoryservice.mapper.ProductInventoryDetailMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * cách cache: 2-tier caching cho page
 * cache product theo từng item và update
 * on delete: check phương pháp mutex lock , soft-delete hoặc stale while revalidate, cursor-based pagination
 */
@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductInventoryDetailMapper productInventoryDetailMapper;
    private final ProductRepository productRepo;
    private final RedisService redisService;
    private final ProductKeyGenerator productKeyGenerator;
    private final ProductAttributeMapper attributeMapper;
    private final ProductResultMapper productResultMapper;

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    private static final Long CACHED_PRODUCT_TTL = 10000L;

    @Autowired
    public ProductService(
            ProductRepository productRepo,
            ProductInventoryDetailMapper productInventoryDetailMapper,
            RedisService redisService,
            ProductKeyGenerator productKeyGenerator,
            ProductAttributeMapper attributeMapper,
            ProductResultMapper productResultMapper
    ) {
        this.productRepo = productRepo;
        this.productInventoryDetailMapper = productInventoryDetailMapper;
        this.redisService = redisService;
        this.productKeyGenerator = productKeyGenerator;
        this.attributeMapper = attributeMapper;
        this.productResultMapper = productResultMapper;
    }

    private void cacheProductPage(String searchTerm, Page<Product> productPage, Pageable pageable) {
        Thread.startVirtualThread(() -> {
            String pageKey = productKeyGenerator.generatePageKey(searchTerm, pageable);
            List<UUID> idList = productPage.getContent().stream().map(Product::getId).toList();
            if (!idList.isEmpty()) {
                redisService.saveItemIds(pageKey, idList);
                Map<String, Product> productKeyMap = productPage.getContent().parallelStream().collect(Collectors.toMap(
                        productKeyGenerator::generateSingleProductKey,
                        Function.identity()
                ));
                redisService.saveProducts(productKeyMap);
            }
        });
    }

    private List<Product> findAllProductInCache(String searchTerm, Pageable pageable) {
        String pageKey = productKeyGenerator.generatePageKey(searchTerm, pageable);
        List<UUID> cachedIds = redisService.getItemIds(pageKey);
        if (cachedIds == null || cachedIds.isEmpty()) {
            return null;
        }
        List<String> keyList = cachedIds.stream().filter(Objects::nonNull)
                .map(productKeyGenerator::generateProductKeyById).filter(Objects::nonNull).toList();
        return redisService.getAllProduct(keyList);
    }

    public Page<ProductResult> findAllProductByName(String searchTerm, Pageable pageable) {
        List<Product> cachedData = findAllProductInCache(searchTerm, pageable);
        if (cachedData != null && !cachedData.isEmpty()) {
            return new PageImpl<>(cachedData.stream().map(productResultMapper::mapObject).toList(), pageable, cachedData.size());
        }

        Page<Product> productPage = isStringEmpty(searchTerm) ? productRepo.findAll(pageable) :
                productRepo.findProductByProductNameContaining(searchTerm, pageable);
        cacheProductPage(searchTerm, productPage, pageable);
        return productPage.map(productResultMapper::mapObject);
    }

    private boolean isStringEmpty(String content) {
        return content == null || content.isBlank();
    }

    public ProductResult findProductById(UUID productId) {
        String cacheKey = productKeyGenerator.generateProductKeyById(productId);
        if (cacheKey == null || cacheKey.isBlank()) {
            return null;
        }

        Product cachedData = redisService.obtainsSingleProduct(cacheKey);
        if (cachedData != null) {
            return productResultMapper.mapObject(cachedData);
        }
        Product product = productRepo.findProductById(productId);
        redisService.saveSingleProduct(cacheKey, product, CACHED_PRODUCT_TTL);
        return product != null ? productResultMapper.mapObject(product) : null;
    }

    @Transactional()
    public ProductInventoryDetailDto createProducts(ProductInventoryDetailDto productDto) {
        Product newProductItem = new Product();
        newProductItem.setProductName(productDto.getProductName());
        newProductItem.setProductAttributes(
                new ProductAttributes(productDto.getProductColor(), productDto.getSize())
        );
        newProductItem.setSku(productDto.getSku());
        Product createdProductItem = productRepo.saveAndFlush(newProductItem);
        return productInventoryDetailMapper.mapObject(createdProductItem);
    }

    @Transactional()
    public Boolean deleteProductById(UUID productId) {
        productRepo.deleteById(productId);
        redisService.deleteProduct(productKeyGenerator.generateProductKeyById(productId));
        redisService.removeCachePageWithRegex(productKeyGenerator.getPagePattern(), DataType.LIST);
        return productRepo.existsById(productId);
    }

    @Transactional
    public ProductResult updateProduct(UUID productId, UpdateProductRequest request) {
        Product product = productRepo.findProductById(productId);
        String newName = request.getName();
        if (newName != null && !newName.isEmpty()) {
            product.setProductName(newName);
        }
        ProductAttributes attributes = product.getProductAttributes();
        attributes.setColor(request.getAttributes().getColor());
        attributes.setSize(request.getAttributes().getSize());
        product.setProductAttributes(attributes);
        return productResultMapper.mapObject(productRepo.saveAndFlush(product));
    }

    @Transactional
    public ResourceCreatedResult<ProductResult> createProducts(CreateProductRequest createProductRequest) {
        Product newProductItem = new Product();
        newProductItem.setProductName(createProductRequest.getName());
        newProductItem.setProductAttributes(
                attributeMapper.mapEntity(createProductRequest.getAttributes())
        );
        newProductItem.setSku(createProductRequest.getSku());
        Product createdProductItem = productRepo.saveAndFlush(newProductItem);
        URI resourceUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProductItem.getId()).toUri();
        return new ResourceCreatedResult<>(resourceUri, productResultMapper.mapObject(createdProductItem));
    }
}