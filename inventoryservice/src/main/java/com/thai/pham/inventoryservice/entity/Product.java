package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

import com.thai.pham.inventoryservice.common.EntityMessage;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    @NotBlank(message = EntityMessage.EMPTY_PRODUCT_SKU_MESSAGE)
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @NotBlank(message = EntityMessage.EMPTY_PRODUCT_NAME_MESSAGE)
    @Column(name = "name", nullable = false)
    private String productName;

    @Column(name = "attributes", nullable = true)
    @Convert(converter = ProductAttributes.ProductAttributesConverter.class)
    private ProductAttributes productAttributes;

    @OneToMany(
        mappedBy = "product", 
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<Inventory> inventory;

    public Product(UUID id, String sku, String productName, ProductAttributes productAttributes) {
        this.id = id;
        this.sku = sku;
        this.productName = productName;
        this.productAttributes = productAttributes;
    }
}