package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.dto.ProductInventoryDetailDto;
import com.thai.pham.inventoryservice.dto.ProductUpdateDto;
import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.entity.ProductAttributes;
import com.thai.pham.inventoryservice.mapper.ProductUpdateDtoMapper;
import com.thai.pham.inventoryservice.repository.ProductRepository;
import com.thai.pham.inventoryservice.mapper.ProductInventoryDetailMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
//**
* cách cache: 2-tier caching cho page
* cache product theo từng item và update
* on delete: check phương pháp mutex lock , soft-delete hoặc stale while revalidate, cursor-based pagination
*/
@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductInventoryDetailMapper productInventoryDetailMapper;
    private final ProductUpdateDtoMapper productUpdateDtoMapper;
    private final ProductRepository productRepo;
    private final RedisService redisService;
    private final ProductKeyGenerator productKeyGenerator;

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    @Autowired
    public ProductService(ProductRepository productRepo, ProductInventoryDetailMapper productInventoryDetailMapper, ProductUpdateDtoMapper productUpdateDtoMapper, RedisService redisService, ProductKeyGenerator productKeyGenerator) {
        this.productRepo = productRepo;
        this.productInventoryDetailMapper = productInventoryDetailMapper;
        this.productUpdateDtoMapper = productUpdateDtoMapper;
        this.redisService = redisService;
        this.productKeyGenerator = productKeyGenerator;
    }

    private void cacheProductPage(String searchTerm, Page<Product> productPage, Pageable pageable) {
        String pageKey = productKeyGenerator.generatePageKey(searchTerm, pageable);
        List<UUID> idList = productPage.getContent().stream().map(Product::getId).toList();
        redisService.saveItemIds(pageKey, idList);
        Map<String, Product> productKeyMap = productPage.getContent().parallelStream().collect(Collectors.toMap(
            redisService::generateSingleProductKey,
            Function.identity()
        ));
        redisService.saveProducts(productKeyMap);
    }

    public List<Product> getAllProduct(Pageable pageable) {
        Page<Product> productPage = productRepo.findAll(pageable);
        cacheProductPage("", productPage, pageable);
        return productPage;
    }

    public Page<Product> getAllVariationOfProduct(String productName, Pageable pageable) {
        Page<Product> productPage = productRepo.findProductByProductNameContaining(productName, pageable);
        cacheProductPage("", productPage, pageable);
        return productPage;
    }

//    @Cacheable(keyGenerator="productPageKeyGenerator")
    public Page<Product> findAllProductByName(String searchTerm, Pageable pageable) {
        Page<Product> productPage = isStringEmpty(searchTerm) ? productRepo.findAll(pageable) :
            productRepo.findProductByProductNameContaining(searchTerm, pageable);
        cacheProductPage(searchTerm, productPage, pageable);
        return productPage;
    }

    private boolean isStringEmpty(String content) {
        return content == null || content.isBlank();
    }

    @Cacheable(keyGenerator="productKeyGenerator")
    public ProductInventoryDetailDto findProductById(UUID productId) {
        Product product = productRepo.findProductById(productId);
        return product != null ? productInventoryDetailMapper.mapObject(product) : null;
    }

    @Transactional(readOnly = false)
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

    @Transactional(readOnly = false)
    @CacheEvict(name = "product", keyGenerator = "productKeyGenerator")
    public Boolean deleteProductById(UUID productId) {
        Product product = productRepo.findProductById(productId);
        if(product == null) {
            return true;
        }
        productRepo.deleteById(productId);
        return productRepo.existsById(productId);
    }

    @Transactional(readOnly = false)
    public ProductUpdateDto updateOrInsertProduct(ProductUpdateDto productUpdateDto) {
        Product product = productUpdateDtoMapper.mapEntity(productUpdateDto);
        return productUpdateDtoMapper.mapObject(productRepo.saveAndFlush(product));
    }

}