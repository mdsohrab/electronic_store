package Electronic.Store.Electronic.Store.services;

import Electronic.Store.Electronic.Store.Dtos.CategoryDto;
import Electronic.Store.Electronic.Store.Dtos.PageableResponse;


public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //get all

    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);


    //get single category
    CategoryDto getCategoryBYId(String categoryId);

    //search

}
