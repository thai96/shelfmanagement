package thai.pham.storageroutingservice.dao;

@Repository
public class LedgerDao implements ReadOnlyLedgerRepository, ReadWriteLedgerRepository {
    private ReadOnlyLedgerRepository readOnlyRepository;
    private ReadWriteLedgerRepository readWriteRepository;

    @Autowired
    public LedgerDao(ReadOnlyLedgerRepository readOnlyRepository, ReadWriteLedgerRepository readWriteRepository) {
        this.readOnlyRepository = readOnlyRepository;
        this.readWriteRepository = readWriteRepository;
    }

    @Override
    public List<Ledger> findAll() {
        return readOnlyRepository.findAll();
    }

    @Override
    public void delete(Ledger ledger) {
        readWriteRepository.delete(ledger);
    }

    @Override
    public void deleteAll() {
        readWriteRepository.deleteAll();
    }

    @Override
    public <S extends Ledger> S save(S s) {
        return readWriteRepository.save(s);
    }

    @Override
    public void flush() {
        readWriteRepository.flush();
    }

    @Override
    public <S extends Ledger> S saveAndFlush(S s) {
        return readWriteRepository.saveAndFlush(s);
    }
}