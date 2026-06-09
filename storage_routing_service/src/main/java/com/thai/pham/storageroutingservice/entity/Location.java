package com.thai.pham.storageroutingservice.entity;

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

    @Column(name = "type", nullable = false, )
    @Convert(converter = LocationType.LocationTypeConverter.class)
    private LocationType locationType;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("false")
    private Boolean isActive;
}