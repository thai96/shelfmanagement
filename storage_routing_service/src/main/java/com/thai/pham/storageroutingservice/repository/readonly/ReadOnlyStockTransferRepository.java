package thai.pham.storageroutingservice.repository.readonly;

@ReadOnlyRepository
public interface ReadOnlyStockTransferRepository extends JpaRepository<Location, UUID> {}