package thai.pham.storageroutingservice.dao;

@Repository
public class ProductDao implements ReadOnlyProductRepository, ReadWriteProductRepository {
    private ReadOnlyProductRepository readOnlyRepository;
    private ReadWriteProductRepository readWriteRepository;

    @Autowired
    public ProductDao(ReadOnlyProductRepository readOnlyRepository, ReadWriteProductRepository readWriteRepository) {
        this.readOnlyRepository = readOnlyRepository;
        this.readWriteRepository = readWriteRepository;
    }

    @Override
    public List<Product> findAll() {
        return readOnlyRepository.findAll();
    }

    @Override
    public void delete(Product product) {
        readWriteRepository.delete(product);
    }

    @Override
    public void deleteAll() {
        readWriteRepository.deleteAll();
    }

    @Override
    public <S extends Product> S save(S s) {
        return readWriteRepository.save(s);
    }

    @Override
    public void flush() {
        readWriteRepository.flush();
    }

    @Override
    public <S extends Product> S saveAndFlush(S s) {
        return readWriteRepository.saveAndFlush(s);
    }
}