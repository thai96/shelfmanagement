package thai.pham.storageroutingservice.repository.readonly;

@ReadOnlyRepository
public interface ReadOnlyProductRepository extends JpaRepository<Location, UUID> {}