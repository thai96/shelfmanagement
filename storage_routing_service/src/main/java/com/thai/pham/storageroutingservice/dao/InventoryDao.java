package thai.pham.storageroutingservice.dao;

@Repository
public class InventoryDao implements ReadOnlyInventoryRepository, ReadWriteInventoryRepository {
    private ReadOnlyInventoryRepository readOnlyRepository;
    private ReadWriteInventoryRepository readWriteRepository;

    @Autowired
    public InventoryDao(ReadOnlyInventoryRepository readOnlyRepository, ReadWriteInventoryRepository readWriteRepository) {
        this.readOnlyRepository = readOnlyRepository;
        this.readWriteRepository = readWriteRepository;
    }

    @Override
    public List<Inventory> findAll() {
        return readOnlyRepository.findAll();
    }

    @Override
    public void delete(Inventory inventory) {
        readWriteRepository.delete(inventory);
    }

    @Override
    public void deleteAll() {
        readWriteRepository.deleteAll();
    }

    @Override
    public <S extends Inventory> S save(S s) {
        return readWriteRepository.save(s);
    }

    @Override
    public void flush() {
        readWriteRepository.flush();
    }

    @Override
    public <S extends Inventory> S saveAndFlush(S s) {
        return readWriteRepository.saveAndFlush(s);
    }
}