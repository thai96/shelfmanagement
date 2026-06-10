package thai.pham.storageroutingservice.service;

@Service
public class ProductService {
    private ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProduct() {
        return productDao.getAll();
    }
}