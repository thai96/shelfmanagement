package thai.pham.storageroutingservice.service;

@Service
public class StockTransferService {
    private StockTransferDao stockTransferDao;

    @Autowired
    public StockTransferService(StockTransferDao stockTransferDao) {
        this.stockTransferDao = stockTransferDao;
    }

    public List<StockTransfer> getAllStockTransfer() {
        return stockTransferDao.getAll();
    }
}