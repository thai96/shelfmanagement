package thai.pham.storageroutingservice.service;

@Service
public class LedgerService {
    private LedgerRepository ledgerRepo;

    @Autowired
    public LedgerService(LedgerRepository ledgerRepo) {
        this.ledgerRepo = ledgerRepo;
    }

    public List<Ledger> getAllLedger() {
        return ledgerRepo.getAll();
    }
}