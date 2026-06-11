package thai.pham.storageroutingservice.repository;

@ReadOnlyRepository
public interface InventoryRepository extends JpaRepository<Location, UUID> {}