package com.thai.pham.storageroutingservice.entity;

@Entity
@Table(name = "STOCK_TRANSFER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public StockTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "attributes", nullable = false)
    @Convert(converter = TransferStatus.TransferStatusConverter.class)
    private TransferStatus transferStatus;

    @ManyToOne(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "from_location", referencedColumnName = "id")
    private Location fromLocation;

    @ManyToOne(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "to_location", referencedColumnName = "id")
    private Location toLocation;
}