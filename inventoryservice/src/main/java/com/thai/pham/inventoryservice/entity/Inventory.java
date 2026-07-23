package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

import com.thai.pham.inventoryservice.common.EntityMessage;

@Entity
@Table(name = "INVENTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseEntity{
    @NotNull(message = EntityMessage.EMPTY_NUMERIC_VALUE_MESSAGE)
    @Min(value = 1, message = EntityMessage.REQUIRED_WHOLE_NUMBER_NUMERIC_VALUE_MESSAGE)
    @Column(name = "qty_on_hand", columnDefinition = "INTEGER NOT NULL CHECK(qty_on_hand >= 0)")
    @ColumnDefault("0")
    private Integer qtyOnHand;

    @NotNull(message = EntityMessage.EMPTY_NUMERIC_VALUE_MESSAGE)
    @Min(value = 1, message = EntityMessage.REQUIRED_WHOLE_NUMBER_NUMERIC_VALUE_MESSAGE)
    @Column(name = "qty_reserved", columnDefinition = "INTEGER NOT NULL CHECK(qty_reserved >= 0)")
    @ColumnDefault("0")
    private Integer qtyReserved;

    @NotNull(message = EntityMessage.EMPTY_NUMERIC_VALUE_MESSAGE)
    @Min(value = 1, message = EntityMessage.REQUIRED_WHOLE_NUMBER_NUMERIC_VALUE_MESSAGE)
    @Column(name = "qty_available", columnDefinition = "INTEGER NOT NULL CHECK(qty_reserved >= 0)")
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