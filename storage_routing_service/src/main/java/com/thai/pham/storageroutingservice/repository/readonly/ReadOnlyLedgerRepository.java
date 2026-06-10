package thai.pham.storageroutingservice.repository.readonly;

@ReadOnlyRepository
public interface ReadOnlyLedgerRepository extends JpaRepository<Location, UUID> {}