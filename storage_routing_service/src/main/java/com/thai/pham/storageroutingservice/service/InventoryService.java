package thai.pham.storageroutingservice.service;

@Service
public class InventoryService {
    private InventoryDao inventoryDao;

    @Autowired
    public InventoryService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    public List<Inventory> getAllInventory() {
        return inventoryDao.getAll();
    }
}