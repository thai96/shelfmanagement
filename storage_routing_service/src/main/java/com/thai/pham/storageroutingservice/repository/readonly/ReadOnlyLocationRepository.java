package thai.pham.storageroutingservice.repository.readonly;

@ReadOnlyRepository
public interface ReadOnlyLocationRepository extends JpaRepository<Location, UUID> {}