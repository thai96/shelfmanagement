package thai.pham.storageroutingservice.repository.readonly;

@ReadOnlyRepository
public interface ReadOnlyInventoryRepository extends JpaRepository<Location, UUID> {}