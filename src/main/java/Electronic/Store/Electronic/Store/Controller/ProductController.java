package Electronic.Store.Electronic.Store.Controller;

import Electronic.Store.Electronic.Store.Dtos.*;
import Electronic.Store.Electronic.Store.services.FileServices;
import Electronic.Store.Electronic.Store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileServices fileServices;

    @Value("${product.profile.image.path}")
    private String imagePath;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);
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

    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image) throws IOException {
        String fileName = fileServices.uploadFile(image, imagePath);
        ProductDto productDto = productService.getSingleProduct(productId);
        productDto.setProductImageName(fileName);
        ProductDto updateProduct = productService.updateProduct(productDto, productId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(updateProduct.getProductImageName())
                .message("Product image is successfull uploaded !!")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }

    //serve image
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.getSingleProduct(productId);
        logger.info("User image name : {}", productDto.getProductImageName());
        InputStream resource = fileServices.getResources(imagePath, productDto.getProductImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }



}
