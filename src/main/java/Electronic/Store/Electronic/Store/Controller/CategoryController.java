package Electronic.Store.Electronic.Store.Controller;

import Electronic.Store.Electronic.Store.Dtos.*;
import Electronic.Store.Electronic.Store.services.CategoryService;
import Electronic.Store.Electronic.Store.services.FileServices;
import Electronic.Store.Electronic.Store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.aspectj.util.PartialOrder;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileServices fileServices;

    @Value("${category.profile.image.path}")
    private String coverImage;

    @Autowired
    private ProductService productService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDts = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDts, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") String categoryId) {
        CategoryDto categoryDts = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDts, HttpStatus.CREATED);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId) {
        categoryService.delete(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("categoryId is delete successfully")
                .success(true).status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        return new ResponseEntity<>(categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    // getSingle
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryBYId(categoryId), HttpStatus.OK);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam("coverImage") MultipartFile image, @PathVariable String categoryId) throws IOException {

        String coverImages = fileServices.uploadFile(image, coverImage);
        CategoryDto categoryDto = categoryService.getCategoryBYId(categoryId);
        categoryDto.setCoverImage(coverImage);

        CategoryDto categoryDtos = categoryService.update(categoryDto, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(coverImages).message("Upload successFully").success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto categoryDto = categoryService.getCategoryBYId(categoryId);
        logger.info("User image name : {}", categoryDto.getCoverImage());
        InputStream resource = fileServices.getResources(coverImage, categoryDto.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }

    @PostMapping("{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable("categoryId") String categoryId,
                                                                @RequestBody ProductDto dto) {

        ProductDto productWithCategory = productService.createWithCategory(dto, categoryId);

        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> assignCategoryToProduct(
            @PathVariable String categoryId,
            @PathVariable String productId) {
        ProductDto productDto = productService.updatedCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> response = productService.getAllCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
