package Electronic.Store.Electronic.Store.Controller;

import Electronic.Store.Electronic.Store.Dtos.ApiResponseMessage;
import Electronic.Store.Electronic.Store.Dtos.PageableResponse;
import Electronic.Store.Electronic.Store.Dtos.ProductDto;
import Electronic.Store.Electronic.Store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProducts(@RequestBody ProductDto productDto,
                                                     @PathVariable("productId") String productId) {
        ProductDto updateProducts = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updateProducts, HttpStatus.CREATED);
    }

    //delete"
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProducts(@PathVariable("productId") String productId) {
        productService.deleteProduct(productId);
        ApiResponseMessage message = new ApiResponseMessage().builder()
                .message("Products deleted successfully")
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //getSingle
    @GetMapping("/productId/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(productService.getSingleProduct(productId), HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String SortDir) {

        return new ResponseEntity<>(productService.getAllProduct(pageNumber, pageSize, sortBy, SortDir), HttpStatus.OK);
    }

    //get all live

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct(

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String SortDir) {

        return new ResponseEntity<>(productService.getAllLiveProduct(pageNumber, pageSize, sortBy, SortDir), HttpStatus.OK);
    }


    //search all
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String SortDir) {

        return new ResponseEntity<>(productService.searchProductByTitle(query, pageNumber, pageSize, sortBy, SortDir), HttpStatus.OK);
    }

}
