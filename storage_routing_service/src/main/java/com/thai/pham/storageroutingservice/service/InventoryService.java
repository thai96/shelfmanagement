package thai.pham.storageroutingservice.service;

@Service
public class InventoryService {
    private InventoryRepository inventoryRepo;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepo.getAll();
    }
}