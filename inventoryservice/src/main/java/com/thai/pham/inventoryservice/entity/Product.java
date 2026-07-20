package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

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