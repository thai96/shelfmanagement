package thai.pham.storageroutingservice.service;

@Service
public class ProductService {
    private ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProduct() {
        return productRepo.getAll();
    }
}