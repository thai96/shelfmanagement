package thai.pham.storageroutingservice.repository;

@ReadOnlyRepository
public interface ProductRepository extends JpaRepository<Location, UUID> {}