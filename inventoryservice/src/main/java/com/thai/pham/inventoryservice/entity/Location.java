package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Convert(converter = LocationType.LocationTypeConverter.class)
    private LocationType locationType;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("false")
    private Boolean isActive;

    @OneToMany(
        mappedBy = "location", 
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<Inventory> inventory;

    @OneToMany(
        mappedBy = "fromLocation",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<StockTransfer> outgoingTransfer;

    @OneToMany(
        mappedBy = "toLocation",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, 
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<StockTransfer> incomingTransfer;

    public Location(UUID id, String name, LocationType locationType, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.locationType = locationType;
        this.isActive = isActive;
    }
}