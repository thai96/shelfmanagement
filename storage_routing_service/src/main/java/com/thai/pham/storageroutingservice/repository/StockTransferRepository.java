package thai.pham.storageroutingservice.repository;

@ReadOnlyRepository
public interface StockTransferRepository extends JpaRepository<Location, UUID> {}