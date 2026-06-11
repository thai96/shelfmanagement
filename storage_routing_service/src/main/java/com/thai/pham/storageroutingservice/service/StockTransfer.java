package thai.pham.storageroutingservice.service;

@Service
public class StockTransferService {
    private StockTransferRepository stockTransferRepo;

    @Autowired
    public StockTransferService(StockTransferRepository stockTransferRepo) {
        this.stockTransferRepo = stockTransferRepo;
    }

    public List<StockTransfer> getAllStockTransfer() {
        return stockTransferRepo.getAll();
    }
}