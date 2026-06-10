package thai.pham.storageroutingservice.dao;

@Repository
public class StockTransferDao implements ReadOnlyStockTransferRepository, ReadWriteStockTransferRepository {
    private ReadOnlyStockTransferRepository readOnlyRepository;
    private ReadWriteStockTransferRepository readWriteRepository;

    @Autowired
    public StockTransferDao(ReadOnlyStockTransferRepository readOnlyRepository, ReadWriteStockTransferRepository readWriteRepository) {
        this.readOnlyRepository = readOnlyRepository;
        this.readWriteRepository = readWriteRepository;
    }

    @Override
    public List<StockTransfer> findAll() {
        return readOnlyRepository.findAll();
    }

    @Override
    public void delete(StockTransfer stockTransfer) {
        readWriteRepository.delete(stockTransfer);
    }

    @Override
    public void deleteAll() {
        readWriteRepository.deleteAll();
    }

    @Override
    public <S extends StockTransfer> S save(S s) {
        return readWriteRepository.save(s);
    }

    @Override
    public void flush() {
        readWriteRepository.flush();
    }

    @Override
    public <S extends StockTransfer> S saveAndFlush(S s) {
        return readWriteRepository.saveAndFlush(s);
    }
}