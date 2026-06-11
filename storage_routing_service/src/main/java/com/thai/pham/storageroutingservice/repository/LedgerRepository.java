package thai.pham.storageroutingservice.repository;

@ReadOnlyRepository
public interface LedgerRepository extends JpaRepository<Location, UUID> {}