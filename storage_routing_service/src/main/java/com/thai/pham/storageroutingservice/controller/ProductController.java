package thai.pham.storageroutingservice.controller;

@RestController
public class ProductController {
    private ProductService productService;

    @AutoWired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }
}