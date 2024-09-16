package Electronic.Store.Electronic.Store.services.impl;

import Electronic.Store.Electronic.Store.Dtos.PageableResponse;
import Electronic.Store.Electronic.Store.Dtos.ProductDto;
import Electronic.Store.Electronic.Store.Entities.CategoryEntity;
import Electronic.Store.Electronic.Store.Entities.ProductEntity;
import Electronic.Store.Electronic.Store.Exceptions.ResourceNotFoundExceptions;
import Electronic.Store.Electronic.Store.Repositories.CategoryRepository;
import Electronic.Store.Electronic.Store.Repositories.ProductRepository;
import Electronic.Store.Electronic.Store.helper.Helper;
import Electronic.Store.Electronic.Store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
        productDto.setAddedDate(new Date());
        ProductEntity savedProduct = productRepository.save(productEntity);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundExceptions("Product not found this given id"));

        productEntity.setTitle(productDto.getTitle());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setStock(productDto.isStock());
        productEntity.setLive(productDto.isLive());
        productEntity.setAddedDate(productDto.getAddedDate());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setQuantity(productDto.getQuantity());
        productEntity.setDiscountedPrice(productDto.getDiscountedPrice());
        productEntity.setProductImageName(productDto.getProductImageName());

        ProductEntity products = productRepository.save(productEntity);
        ProductDto productDtos = modelMapper.map(products, ProductDto.class);

        return productDtos;
    }

    @Override
    public void deleteProduct(String productId) {

        ProductEntity products = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Product Id not found!!"));

        productRepository.delete(products);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {

        ProductEntity products = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Product Id not found !!"));

        ProductDto productDto = modelMapper.map(products, ProductDto.class);
        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<ProductEntity> productEntities = productRepository.findAll(pageable);

        PageableResponse<ProductDto> productDtoPageableResponse = Helper.getPageableResponse(productEntities, ProductDto.class);
        return productDtoPageableResponse;
    }


    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<ProductEntity> productEntities = productRepository.findByLiveTrue(pageable);

        return Helper.getPageableResponse(productEntities, ProductDto.class);
    }


    @Override
    public PageableResponse<ProductDto> searchProductByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<ProductEntity> productEntities = productRepository.findByTitleContaining(subTitle, pageable);

        return Helper.getPageableResponse(productEntities, ProductDto.class);
    }

    //create product with category
    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExceptions("Category id not found !!"));

        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
        productEntity.setCategory(categoryEntity);
        productDto.setAddedDate(new Date());
        ProductEntity savedProduct = productRepository.save(productEntity);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    //update category of product
    @Override
    public ProductDto updatedCategory(String productId, String categoryId) {

        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundExceptions("Product Id not found"));

        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExceptions("Category id not found"));

        product.setCategory(category);

        ProductEntity savedProducts = productRepository.save(product);

        return modelMapper.map(savedProducts, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExceptions("Category id not found !!"));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<ProductEntity> page = productRepository.findByCategory(category, pageable);

        return Helper.getPageableResponse(page, ProductDto.class);
    }

}
