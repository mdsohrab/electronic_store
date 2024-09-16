package Electronic.Store.Electronic.Store.services;

import Electronic.Store.Electronic.Store.Dtos.PageableResponse;
import Electronic.Store.Electronic.Store.Dtos.ProductDto;
import Electronic.Store.Electronic.Store.Entities.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    //create
    ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto updateProduct(ProductDto productDto, String productId);

    //delete
    void deleteProduct(String productId);

    //getSingle
    ProductDto getSingleProduct(String productId);

    //getAll
    PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all: live
    PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search product
    PageableResponse<ProductDto> searchProductByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);


    //create product with category
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    //update category of product
    ProductDto updatedCategory(String productId, String categoryId);

    PageableResponse<ProductDto> getAllCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
    //others
}
