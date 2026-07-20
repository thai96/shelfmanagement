package com.thai.pham.inventoryservice.entity;

@Data
@MappedSuperClass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
}