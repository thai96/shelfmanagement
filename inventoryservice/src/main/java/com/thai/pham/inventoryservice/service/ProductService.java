package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductInventoryDetailMapper productInventoryDetailMapper;
    private final ProductRepository productRepo;

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    @Autowired
    public ProductService(ProductRepository productRepo, ProductInventoryDetailMapper productInventoryDetailMapper) {
        this.productRepo = productRepo;
        this.productInventoryDetailMapper = productInventoryDetailMapper;
    }

    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    public Page<Product> getAllVariationOfProduct(String productName, Pageable pageable) {
        return productRepo.findProductByProductNameContaining(productName, pageable);
    }

    @Cacheable(keyGenerator="productPageKeyGenerator")
    public Page<Product> findAllProductByName(String searchTerm, Pageable pageable) {
        if(isStringEmpty(searchTerm)) {
            return productRepo.findAll(pageable);
        }
        return productRepo.findProductByProductNameContaining(searchTerm, pageable);
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
        productRepo.persist(newProductItem);
        return productInventoryDetailMapper.mapObject(newProductItem);
    }

    @Transactional(readOnly = false)
    public Boolean deleteProductById(UUID productId) {
        Product product = productRepo.findProductById(productId);
        if(product == null) {
            return true;
        }
        productRepo.deleteProductById(productId);
        return productRepo.existsById(productId);
    }
}