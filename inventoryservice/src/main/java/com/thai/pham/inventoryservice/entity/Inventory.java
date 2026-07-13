package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Table(name = "INVENTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "qty_on_hand", columnDefinition = "INTEGER NON NULL CHECK(qty_on_hand >= 0)")
    @ColumnDefault("0")
    private Integer qtyOnHand;

    @Column(name = "qty_reserved", columnDefinition = "INTEGER NON NULL CHECK(qty_reserved >= 0)")
    @ColumnDefault("0")
    private Integer qtyReserved;

    @Column(name = "qty_available", columnDefinition = "INTEGER NON NULL CHECK(qty_reserved >= 0)")
    @ColumnDefault("0")
    private Integer qtyAvailable;

    @ManyToOne(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Version
    private Integer version;

    public Inventory(UUID id, Integer qtyOnHand, Integer qtyReserved, Integer qtyAvailable, Location location, Product product) {
        this.id = id;
        this.qtyOnHand = qtyOnHand;
        this.qtyReserved = qtyReserved;
        this.qtyAvailable = qtyAvailable;
        this.location = location;
        this.product = product;
    }
}