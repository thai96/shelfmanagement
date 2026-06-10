package thai.pham.storageroutingservice.service;

@Service
public class LedgerService {
    private LedgerDao ledgerDao;

    @Autowired
    public LedgerService(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }

    public List<Ledger> getAllLedger() {
        return ledgerDao.getAll();
    }
}