package Electronic.Store.Electronic.Store.services.impl;

import Electronic.Store.Electronic.Store.Dtos.CategoryDto;
import Electronic.Store.Electronic.Store.Dtos.PageableResponse;
import Electronic.Store.Electronic.Store.Entities.CategoryEntity;
import Electronic.Store.Electronic.Store.Exceptions.ResourceNotFoundExceptions;
import Electronic.Store.Electronic.Store.Repositories.CategoryRepository;
import Electronic.Store.Electronic.Store.helper.Helper;
import Electronic.Store.Electronic.Store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        CategoryEntity categoryEntity = dtoToEntity(categoryDto);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);

        return entityToDto(savedCategory);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExceptions("Category not found !!"));

        categoryEntity.setTitle(categoryDto.getTitle());
        categoryEntity.setDescription(categoryDto.getDescription());
        categoryEntity.setCoverImage(categoryDto.getCoverImage());

        CategoryEntity updated = categoryRepository.save(categoryEntity);
        CategoryDto categoryDtos = entityToDto(updated);
        return categoryDtos;
    }

    @Override
    public void delete(String categoryId) {

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExceptions("Category id not found!!"));
        categoryRepository.delete(categoryEntity);


    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<CategoryEntity> page = categoryRepository.findAll(pageable);

        return Helper.getPageableResponse(page, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryBYId(String categoryId) {

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExceptions("Category Id not found!!"));
        CategoryDto categoryDto = entityToDto(categoryEntity);
        return categoryDto;
    }

    private CategoryDto entityToDto(CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    private CategoryEntity dtoToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, CategoryEntity.class);
    }
}
