package com.dragon.shoppingCart.service;

import com.dragon.shoppingCart.entity.Category;
import com.dragon.shoppingCart.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);
    List<Category> findAll();
    Category addCategory(CategoryDto categoryDto);
    Category findCategoryByName(String catName);
    Category updateExisitingCategory(CategoryDto categoryDto, Long id);
    void deleteCategory(Long id);

}
