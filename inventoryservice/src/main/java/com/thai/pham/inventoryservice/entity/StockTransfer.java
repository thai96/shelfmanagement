package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import com.thai.pham.inventoryservice.common.EntityMessage;

@Entity
@Table(name = "STOCK_TRANSFER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockTransfer extends BaseEntity {
    @NotNull(message = EntityMessage.NULL_TRANSFER_STATUS_MESSAGE)
    @Column(name = "attributes", nullable = false)
    @Convert(converter = TransferStatus.TransferStatusConverter.class)
    private TransferStatus transferStatus;

    @NotNull(message = EntityMessage.NULL_PRODUCT_TRANSFER_START_LOCATION_MESSAGE)
    @ManyToOne(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "from_location", referencedColumnName = "id")
    private Location fromLocation;

    @NotNull(message = EntityMessage.NULL_PRODUCT_TRANSFER_END_LOCATION_MESSAGE)
    @ManyToOne(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "to_location", referencedColumnName = "id")
    private Location toLocation;

    public StockTransfer(UUID id, TransferStatus transferStatus) {
        this.id = id;
        this.transferStatus = transferStatus;
    }
}