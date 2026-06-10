package com.thai.pham.storageroutingservice.entity;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

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
}