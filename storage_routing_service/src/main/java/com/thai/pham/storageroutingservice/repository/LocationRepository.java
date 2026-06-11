package thai.pham.storageroutingservice.repository;

@ReadOnlyRepository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    public Page<Location> findLocationByLocationType(LocationType locationType, Pageable pageable);

    public Page<Location> findLocationByNameContaining(String name, Pageable pageable);
}