package com.dragon.shoppingCart.controller;

import com.dragon.shoppingCart.entity.Category;
import com.dragon.shoppingCart.model.CategoryDto;
import com.dragon.shoppingCart.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    CategoryService categoryService;
    CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll(){
        return categoryService.findAll();
    }
    @GetMapping("/{catId}")
    public Category findById(@PathVariable Long catId){
        return categoryService.findById(catId);
    }
    @GetMapping("/byName/{name}")
    public Category findByName(@PathVariable String name){
        return categoryService.findCategoryByName(name);
    }
    @PostMapping
    public Category add(@RequestBody CategoryDto categoryDto){

        return categoryService.addCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId){
        categoryService.deleteCategory(catId);

    }
    @PutMapping("/{catId}")
    public Category update(@RequestBody CategoryDto categoryDto,@PathVariable Long catId){
        return categoryService.updateExisitingCategory(categoryDto,catId);
    }
}
