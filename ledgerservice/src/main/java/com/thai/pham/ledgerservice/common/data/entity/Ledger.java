package com.thai.pham.ledgerservice.common.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SuperBuilder;

import java.util.UUID;
import java.time.LocalDateTime;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "LEDGER")
@EntityListeners(AuditingEntityListener.class)
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NonNull(message = MsgConst.NON_NULL_LOCATION_MSG)
    @Column(name = "location_id", updatable = false, nullable = false)
    private UUID locationId;

    @NonNull(message = MsgConst.NON_NULL_PRODUCT_MSG)
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID productId;

    @Column(name = "qty_change", update = false, nullable = false)
    private Integer qtyChange;

    @NonNull(message = MsgConst.NON_NULL_UPDATE_REASON_MSG)
    @Column(name = "reason", update = false, nullable = false)
    @Convert(converter = UpdateReason.UpdateReasonConverter.class)
    private UpdateReason updateReason;

    @NonNull(message = MsgConst.NON_NULL_REF_ID_MSG)
    @Column(name = "ref_id", update = false, nullable = false)
    private String refId;

    @CreatedDate
    @Column(name = "created_at", update = false, nullable = false)
    private LocalDateTime createdAt;
}